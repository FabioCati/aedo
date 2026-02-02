package com.fabiocati.aedo.tvdbservice

import arrow.core.Either
import com.fabiocati.aedo.models.Movie
import com.fabiocati.aedo.models.MovieDetails

interface MoviesApi {
    suspend fun getPopularMovies(page: Int): Either<Exception, List<Movie>>
    suspend fun getMovieDetails(movieId: Int): MovieDetails
}
