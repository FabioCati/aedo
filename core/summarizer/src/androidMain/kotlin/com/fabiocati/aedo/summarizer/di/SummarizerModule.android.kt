package com.fabiocati.aedo.summarizer.di

import com.fabiocati.aedo.summarizer.AndroidReviewSummarizer
import com.fabiocati.aedo.summarizer.ReviewSummarizer
import org.koin.dsl.module

actual val platformSummarizerModule = module {
    single<ReviewSummarizer> { AndroidReviewSummarizer(get()) }
}
