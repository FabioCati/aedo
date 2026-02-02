package com.fabiocati.aedo.tvdbservice

import arrow.core.Either
import com.fabiocati.aedo.models.Movie

interface MoviesApi {
    suspend fun getPopularMovies(page: Int): Either<Exception, List<Movie>>
}
