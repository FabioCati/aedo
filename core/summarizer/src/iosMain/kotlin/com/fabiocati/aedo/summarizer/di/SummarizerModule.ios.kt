package com.fabiocati.aedo.summarizer.di

import com.fabiocati.aedo.summarizer.ReviewSummarizer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.dsl.module

class IosReviewSummarizer : ReviewSummarizer {
    override val status: StateFlow<ReviewSummarizer.Status> = MutableStateFlow(ReviewSummarizer.Status.NotAvailable).asStateFlow()
    override fun downloadModel() {}
    override suspend fun summarize(reviews: List<String>): String = "Not supported on iOS yet"
}

actual val platformSummarizerModule = module {
    single<ReviewSummarizer> { IosReviewSummarizer() }
}
