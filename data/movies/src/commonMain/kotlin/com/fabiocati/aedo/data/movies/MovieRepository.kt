package com.fabiocati.aedo.data.movies

import arrow.core.Either
import com.fabiocati.aedo.models.Movie

interface MovieRepository {

    suspend fun getPopularMovies(): Either<Exception, List<Movie>>
}