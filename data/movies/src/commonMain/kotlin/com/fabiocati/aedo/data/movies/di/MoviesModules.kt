package com.fabiocati.aedo.data.movies.di

import com.fabiocati.aedo.data.movies.MovieRepository
import com.fabiocati.aedo.data.movies.internal.MovieRepositoryImpl
import com.fabiocati.aedo.tvdbservice.di.TvdbServiceModule
import org.koin.dsl.module

object MoviesModule {
    fun get() = module {
        single<MovieRepository> {
            MovieRepositoryImpl(get())
        }
        includes(TvdbServiceModule.get())
    }
}