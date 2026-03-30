package com.fabiocati.aedo.summarizer

import android.content.Context
import com.fabiocati.aedo.models.Review
import com.google.mlkit.genai.common.DownloadCallback
import com.google.mlkit.genai.common.FeatureStatus
import com.google.mlkit.genai.common.GenAiException
import com.google.mlkit.genai.summarization.Summarization
import com.google.mlkit.genai.summarization.SummarizationRequest
import com.google.mlkit.genai.summarization.Summarizer
import com.google.mlkit.genai.summarization.SummarizerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext

class AndroidReviewSummarizer(
    private val context: Context,
) : ReviewSummarizer {

    private val summarizer by lazy {
        val options = SummarizerOptions.builder(context)
            .setInputType(SummarizerOptions.InputType.ARTICLE)
            .setOutputType(SummarizerOptions.OutputType.ONE_BULLET)
            .setLongInputAutoTruncationEnabled(true)
            .build()
        Summarization.getClient(options)
    }

    init {
        checkStatus()
    }

    private val _status = MutableStateFlow<ReviewSummarizer.Status>(ReviewSummarizer.Status.NotAvailable)
    override val status: StateFlow<ReviewSummarizer.Status> = _status.asStateFlow()

    private val _conversation = MutableStateFlow<String>("")
    override val conversation: StateFlow<String> = _conversation.asStateFlow()

    private fun checkStatus() {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val featureStatus = summarizer.checkFeatureStatus().get()

            when (featureStatus) {
                FeatureStatus.AVAILABLE -> {
                    _status.value = ReviewSummarizer.Status.Available(1f)
                }

                FeatureStatus.DOWNLOADING -> {
                    _status.value = ReviewSummarizer.Status.Downloading

                }

                FeatureStatus.UNAVAILABLE, FeatureStatus.DOWNLOADABLE -> {
                    _status.value = ReviewSummarizer.Status.NotAvailable
                }
            }
            cancel()
        }
    }

    override fun downloadModel() {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val featureStatus = _status.value

            if (featureStatus is ReviewSummarizer.Status.Available || featureStatus is ReviewSummarizer.Status.Downloading) {
                return@launch
            }

            _status.value = ReviewSummarizer.Status.Downloading
            val featureDownloaded = summarizer.downloadFeatureSync()
            if (featureDownloaded) {
                _status.value = ReviewSummarizer.Status.Available(1f)
            } else {
                _status.value = ReviewSummarizer.Status.NotAvailable
            }
            cancel()
        }

    }

    override suspend fun summarize(reviews: List<Review>): String {
        val text = reviews.joinToString("\n\n") { it.content }
        val request = SummarizationRequest.builder(text).build()
        return try {
            val result = withContext(Dispatchers.IO) {
                summarizer.runInference(request).get()
            }
            val summary = result.summary.substringAfter("*").trim()
            _conversation.value += summary
            summary
        } catch (e: Exception) {
            val error = "Failed to summarize reviews: ${e.message}"
            _conversation.value += error
            error
        }
    }

    private suspend fun Summarizer.downloadFeatureSync() = suspendCancellableCoroutine { continuation ->
        downloadFeature(
            object : DownloadCallback {
                override fun onDownloadCompleted() {
                    continuation.resumeWith(Result.success(true))
                }

                override fun onDownloadFailed(p0: GenAiException) {
                    continuation.resumeWith(Result.success(false))
                }

                override fun onDownloadProgress(p0: Long) {}

                override fun onDownloadStarted(p0: Long) {}
            }
        )
    }

    override fun close() {
        summarizer.close()
    }
}
