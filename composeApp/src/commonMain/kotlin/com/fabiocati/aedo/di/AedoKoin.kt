package com.fabiocati.aedo.di

import com.fabiocati.aedo.data.movies.di.MoviesModule
import com.fabiocati.aedo.screens.home.HomeViewModel
import com.fabiocati.aedo.screens.movieDetails.MovieDetailsViewModel
import com.fabiocati.aedo.screens.settings.SettingsViewModel
import com.fabiocati.aedo.summarizer.di.summarizerModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinConfiguration
import org.koin.dsl.includes
import org.koin.dsl.module

fun initKoin(
    configuration: KoinConfiguration? = null,
    platformModule: Module? = null
) {
    startKoin {
        includes(configuration)
        if (platformModule != null) {
            modules(platformModule)
        }
        modules(
            MoviesModule.get(),
            summarizerModule
        )
        modules(
            module {
                viewModel {
                    HomeViewModel(
                        get()
                    )
                }
                viewModel { params ->
                    MovieDetailsViewModel(
                        movieId = params.get(),
                        movieRepository = get(),
                        reviewSummarizer = get()
                    )
                }
                viewModel {
                    SettingsViewModel(settingsRepository = get())
                }
            }
        )
    }
}