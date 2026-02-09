package com.fabiocati.aedo.tvdbservice

import arrow.core.Either
import com.fabiocati.aedo.models.Movie
import com.fabiocati.aedo.models.MovieDetails
import com.fabiocati.aedo.models.StreamingService

interface MoviesApi {
    suspend fun getPopularMovies(page: Int): Either<Exception, List<Movie>>
    suspend fun getTrendingMovies(page: Int): Either<Exception, List<Movie>>
    suspend fun getStreamingServiceMovies(streamingService: StreamingService, page: Int): Either<Exception, List<Movie>>
    suspend fun getMovieDetails(movieId: Int): MovieDetails
    suspend fun getSimilarMovies(movieId: Int): Either<Exception, List<Movie>>
}
