package com.fabiocati.aedo.data.movies

import arrow.core.Either
import com.fabiocati.aedo.models.Movie
import com.fabiocati.aedo.models.MovieDetails
import com.fabiocati.aedo.models.StreamingService

interface MovieRepository {
    suspend fun getPopularMovies(): Either<Exception, List<Movie>>
    suspend fun getTrendingMovies(): Either<Exception, List<Movie>>
    suspend fun getStreamingServiceMovies(streamingService: StreamingService): Either<Exception, List<Movie>>
    suspend fun getMovieDetails(movieId: Int): MovieDetails
    suspend fun getSimilarMovies(movieId: Int): Either<Exception, List<Movie>>
}