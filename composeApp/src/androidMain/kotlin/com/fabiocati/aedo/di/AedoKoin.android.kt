package com.fabiocati.aedo.di

import com.fabiocati.aedo.AndroidUrlOpener
import com.fabiocati.aedo.UrlOpener
import org.koin.core.module.Module
import org.koin.dsl.module

internal fun getPlatformModule(): Module = module {
    factory<UrlOpener> {
        AndroidUrlOpener(get())
    }
}