package com.fabiocati.aedo.summarizer.di

import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformSummarizerModule: Module

val summarizerModule = module {
    includes(platformSummarizerModule)
}
