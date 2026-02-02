package com.fabiocati.aedo.data.movies.internal

import arrow.core.Either
import com.fabiocati.aedo.data.movies.MovieRepository
import com.fabiocati.aedo.models.Movie
import com.fabiocati.aedo.models.MovieDetails
import com.fabiocati.aedo.tvdbservice.MoviesApi

internal class MovieRepositoryImpl(
    private val api: MoviesApi
) : MovieRepository {

    override suspend fun getPopularMovies(): Either<Exception, List<Movie>> {
        return api.getPopularMovies(page = 1)
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetails = api.getMovieDetails(movieId = movieId)
}