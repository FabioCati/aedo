package com.fabiocati.aedo.di

import com.fabiocati.aedo.data.movies.di.MoviesModule
import com.fabiocati.aedo.screens.home.HomeViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinConfiguration
import org.koin.dsl.includes
import org.koin.dsl.module

fun initKoin(configuration: KoinConfiguration? = null) {
    startKoin {
        includes(configuration)
        modules(
            MoviesModule.get()
        )
        modules(
            module {
                viewModel {
                    HomeViewModel(
                        get()
                    )
                }
            })

    }
}