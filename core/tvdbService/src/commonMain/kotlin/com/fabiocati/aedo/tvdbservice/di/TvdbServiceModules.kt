package com.fabiocati.aedo.tvdbservice.di

import app.moviebase.tmdb.Tmdb3
import com.fabiocati.aedo.tvdbservice.MoviesApi
import com.fabiocati.aedo.tvdbservice.internal.api.MoviesApiImpl
import com.fabiocati.aedo.tvdbservice.internal.mapper.StreamingServiceMapper
import com.fabiocati.aedo.tvdbservice.internal.mapper.TmdbMovieMapper
import com.fabiocati.aedo.tvdbservice.internal.mapper.TmdbTrailerMapper
import org.koin.dsl.module
import secrets.Secrets


object TvdbServiceModule {
    fun get() = module {
        single<MoviesApi> {
            MoviesApiImpl(
                tmdb = get<Tmdb3>(),
                mapper = get<TmdbMovieMapper>(),
                streamingServiceMapper = get<StreamingServiceMapper>()
            )
        }
        single<TmdbMovieMapper> {
            TmdbMovieMapper(
                tmdb = get<Tmdb3>(),
                trailerMapper = get<TmdbTrailerMapper>()
            )
        }
        single<Tmdb3> {
            Tmdb3 {
                tmdbApiKey = Secrets.TVDB_API_KEY
                useTimeout = false
                maxRequestRetries = null
            }
        }
        factory {
            TmdbTrailerMapper()
        }
        factory {
            StreamingServiceMapper()
        }
    }
}