package com.fabiocati.aedo.summarizer

import com.fabiocati.aedo.models.Review
import kotlinx.coroutines.flow.StateFlow

interface ReviewSummarizer {
    sealed interface Status {
        object NotAvailable : Status
        object Downloading : Status
        data class Available(val progress: Float) : Status
        data class Error(val message: String) : Status
    }

    val status: StateFlow<Status>
    val conversation: StateFlow<String>

    fun downloadModel()

    suspend fun summarize(reviews: List<Review>): String
    fun close()
}
