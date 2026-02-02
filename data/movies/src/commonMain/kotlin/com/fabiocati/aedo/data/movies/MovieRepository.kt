package com.fabiocati.aedo.data.movies

import arrow.core.Either
import com.fabiocati.aedo.models.Movie
import com.fabiocati.aedo.models.MovieDetails

interface MovieRepository {
    suspend fun getPopularMovies(): Either<Exception, List<Movie>>
    suspend fun getMovieDetails(movieId: Int): MovieDetails
}