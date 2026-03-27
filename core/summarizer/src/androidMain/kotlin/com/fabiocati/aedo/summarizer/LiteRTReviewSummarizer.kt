package com.fabiocati.aedo.summarizer

import com.fabiocati.aedo.persistence.SettingsRepository
import com.google.ai.edge.litertlm.Conversation
import com.google.ai.edge.litertlm.Engine
import com.google.ai.edge.litertlm.EngineConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
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
            val config = EngineConfig(modelPath = modelFile.absolutePath)
            val newEngine = Engine(config)
            _status.value = ReviewSummarizer.Status.Downloading
            newEngine.initialize()
            engine = newEngine
            _status.value = ReviewSummarizer.Status.Available(1f)
        } catch (e: Exception) {
            _status.value = ReviewSummarizer.Status.Error("Failed to initialize LiteRT Engine: ${e.message}")
        }

    }

    override suspend fun summarize(reviews: List<String>): String {
        if (engine == null) checkModelStatus()
        val currentEngine = engine ?: return "Summarizer not ready. Check settings."

        val prompt = "Please summarize the following reviews:\n\n${reviews.joinToString("\n\n")}" +
                "Keep only the answer without saying anything about the prompt"

        return try {
            currentConversation = currentEngine.createConversation()

            val message = currentConversation?.sendMessage(prompt)

            currentConversation?.close()
            message.toString().trim()
        } catch (e: Exception) {
            _status.value = ReviewSummarizer.Status.Error("$e")
            "Failed to summarize: ${e.message}"
        }
    }

    override fun close() {
        if(currentConversation?.isAlive == true){
            currentConversation?.close()
        }
        scope.cancel()
        engine?.close()
        engine = null
    }
}
