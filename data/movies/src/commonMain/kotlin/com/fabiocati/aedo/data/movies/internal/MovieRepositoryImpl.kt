package com.fabiocati.aedo.data.movies.internal

import arrow.core.Either
import arrow.core.right
import com.fabiocati.aedo.data.movies.MovieRepository
import com.fabiocati.aedo.models.Movie
import com.fabiocati.aedo.models.MovieDetails
import com.fabiocati.aedo.models.StreamingService
import com.fabiocati.aedo.persistence.MovieLocalDataSource
import com.fabiocati.aedo.tvdbservice.MoviesApi

internal class MovieRepositoryImpl(
    private val api: MoviesApi,
    private val localDataSource: MovieLocalDataSource
) : MovieRepository {

    override suspend fun getPopularMovies(): Either<Exception, List<Movie>> {
        val popularMovies = api.getPopularMovies(page = 1)
        if (popularMovies is Either.Left) {
            return localDataSource.getPopularMovies().right()
        }
        popularMovies as Either.Right
        localDataSource.savePopularMovies(popularMovies.value)
        return popularMovies
    }

    override suspend fun getTrendingMovies(): Either<Exception, List<Movie>> {
        val trendingMovies = api.getTrendingMovies(page = 1)
        if (trendingMovies is Either.Left) {
            return localDataSource.getTrendingMovies().right()
        }
        trendingMovies as Either.Right
        localDataSource.saveTrendingMovies(trendingMovies.value)
        return trendingMovies
    }

    override suspend fun getStreamingServiceMovies(streamingService: StreamingService): Either<Exception, List<Movie>> {
        val streamingServiceMovies = api.getStreamingServiceMovies(streamingService, page = 1)
        if (streamingServiceMovies is Either.Left) {
            return localDataSource.getStreamingServiceMovies(streamingService).right()
        }
        streamingServiceMovies as Either.Right
        localDataSource.saveStreamingServiceMovies(streamingService, streamingServiceMovies.value)
        return streamingServiceMovies
    }

    override suspend fun getMovieDetails(movieId: Int): Either<Exception, MovieDetails> = api.getMovieDetails(movieId = movieId)

    override suspend fun getSimilarMovies(movieId: Int): Either<Exception, List<Movie>> {
        val similarMovies = api.getSimilarMovies(movieId = movieId)
        if (similarMovies is Either.Left) {
            return localDataSource.getSimilarMovies(movieId).right()
        }
        similarMovies as Either.Right
        localDataSource.saveSimilarMovies(movieId, similarMovies.value)
        return similarMovies
    }
}