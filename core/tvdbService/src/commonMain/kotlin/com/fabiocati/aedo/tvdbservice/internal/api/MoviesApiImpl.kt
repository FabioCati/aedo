package com.fabiocati.aedo.tvdbservice.internal.api

import app.moviebase.tmdb.Tmdb3
import arrow.core.Either
import com.fabiocati.aedo.models.Movie
import com.fabiocati.aedo.tvdbservice.MoviesApi
import com.fabiocati.aedo.tvdbservice.internal.mapper.TmdbMovieMapper
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

internal class MoviesApiImpl(
    private val tmdb: Tmdb3,
    private val mapper: TmdbMovieMapper
) : MoviesApi {

    override suspend fun getPopularMovies(page: Int): Either<Any?, List<Movie>> {
        val moviesResult = try {
            tmdb.movies.popular(
                page = page
            ).results
        } catch (e: Exception) {
            return Either.Left(e)
        }
        val movies = coroutineScope {
            moviesResult.map { movie ->
                async {
                    mapper.toMovie(movie)
                }
            }.awaitAll()
        }
        return Either.Right(movies)
    }

}