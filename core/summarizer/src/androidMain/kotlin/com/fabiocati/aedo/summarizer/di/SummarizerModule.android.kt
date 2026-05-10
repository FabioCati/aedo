package com.fabiocati.aedo.summarizer.di

import com.fabiocati.aedo.persistence.SettingsRepository
import com.fabiocati.aedo.summarizer.AndroidReviewSummarizer
import com.fabiocati.aedo.summarizer.LiteRTReviewSummarizer
import com.fabiocati.aedo.summarizer.ReviewSummarizer
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformSummarizerModule = module {
    factory<ReviewSummarizer> { 
        val settingsRepository = get<SettingsRepository>()
        if (settingsRepository.getUseLiteRT()) {
            LiteRTReviewSummarizer(settingsRepository)
        } else {
            AndroidReviewSummarizer(androidContext())
        }
    }
}
