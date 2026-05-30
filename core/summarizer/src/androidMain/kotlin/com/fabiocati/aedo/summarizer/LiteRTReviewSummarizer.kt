package com.fabiocati.aedo.summarizer

import com.fabiocati.aedo.models.Review
import com.fabiocati.aedo.persistence.SettingsRepository
import com.google.ai.edge.litertlm.Backend
import com.google.ai.edge.litertlm.Conversation
import com.google.ai.edge.litertlm.ConversationConfig
import com.google.ai.edge.litertlm.Engine
import com.google.ai.edge.litertlm.EngineConfig
import com.google.ai.edge.litertlm.Tool
import com.google.ai.edge.litertlm.ToolParam
import com.google.ai.edge.litertlm.ToolSet
import com.google.ai.edge.litertlm.tool
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class LiteRTReviewSummarizer(
    private val settingsRepository: SettingsRepository,
) : ReviewSummarizer {

    private val scope = CoroutineScope(Job() + Dispatchers.IO)
    private var engine: Engine? = null
    private var currentConversation: Conversation? = null

    private val _status = MutableStateFlow<ReviewSummarizer.Status>(ReviewSummarizer.Status.NotAvailable)
    override val status: StateFlow<ReviewSummarizer.Status> = _status.asStateFlow()

    private val _conversation = MutableStateFlow<String>("")
    override val conversation: StateFlow<String> = _conversation.asStateFlow()

    init {
        checkModelStatus()
        downloadModel()
    }

    override fun downloadModel() {
        scope.launch {
            val customPath = settingsRepository.getLiteRTModelPath()
            if (customPath == null) {
                _status.value = ReviewSummarizer.Status.Error("No model path set. Please select a LiteRT model in settings.")
                return@launch
            }
            val fileToUse = File(customPath)
            initializeEngine(fileToUse)
        }
    }

    private fun checkModelStatus() {
        val customPath = settingsRepository.getLiteRTModelPath()
        if (customPath == null) {
            _status.value = ReviewSummarizer.Status.Error("No model path set. Please select a LiteRT model in settings.")
            return
        }

        val fileToUse = File(customPath)

        if (!fileToUse.exists() || engine == null || !engine?.isInitialized()!!) {
            _status.value = ReviewSummarizer.Status.NotAvailable
        } else {
            _status.value = ReviewSummarizer.Status.Error("Model file not found at: $customPath")
        }
    }

    private suspend fun initializeEngine(modelFile: File) {
        if (engine != null) return
        if (status.value == ReviewSummarizer.Status.Downloading) return
        try {
            val config = EngineConfig(
                modelPath = modelFile.absolutePath,
                backend = Backend.CPU()
            )
            val newEngine = Engine(config)
            _status.value = ReviewSummarizer.Status.Downloading
            newEngine.initialize()
            engine = newEngine
            _status.value = ReviewSummarizer.Status.Available(1f)
        } catch (e: Exception) {
            _status.value = ReviewSummarizer.Status.Error("Failed to initialize LiteRT Engine: ${e.message}")
        }
    }

    override suspend fun summarize(reviews: List<Review>): String {
        if (engine == null) checkModelStatus()
        val currentEngine = engine ?: return "Summarizer not ready. Check settings."

        val prompt = "Please summarize the following reviews:\n\n${reviews.joinToString("\n\n") { it.content }}" +
                "Keep only the answer without saying anything about the prompt. Use max 100 words. Show only the summary." +
                "Show at the end the Average rating for that movie. Here are the ratings : ${reviews.joinToString(", ") { it.rating.toString() }}"
        return try {
            currentConversation = currentEngine.createConversation(
                conversationConfig = ConversationConfig(
                    tools = listOf(
                        tool(AverageToolSet())
                    ),
                )
            )

            currentConversation?.sendMessageAsync(prompt)?.collect { it ->
                _conversation.value += it.toString()
            }

            currentConversation?.close()
            val result = _conversation.value.trim()
            _conversation.value = ""
            result
        } catch (e: Exception) {
            _status.value = ReviewSummarizer.Status.Error("$e")
            val error = "Failed to summarize: ${e.message}"
            _conversation.value += error
            error
        }
    }

    override fun close() {
        if (currentConversation?.isAlive == true) {
            currentConversation?.close()
        }
        scope.cancel()
        engine?.close()
        engine = null
    }
}

class AverageToolSet : ToolSet {
    @Tool(description = "Calculates the arithmetic mean of a list of numeric movie ratings. Use this tool when you need to provide a precise average rating based on the provided review scores.")
    fun getAverageRating(
        @ToolParam(description = "A list of floating-point numbers representing individual review scores (e.g., [8.5, 7.0, 9.0]). Ratings typically range from 1.0 to 10.0.") ratings: List<Double> = emptyList(),
    ): Double {
        return ratings.average()
    }
}
