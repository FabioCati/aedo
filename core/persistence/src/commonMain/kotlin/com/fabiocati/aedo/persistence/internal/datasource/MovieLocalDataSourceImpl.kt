package com.fabiocati.aedo.persistence.internal.datasource

import com.fabiocati.aedo.models.Movie
import com.fabiocati.aedo.models.StreamingService
import com.fabiocati.aedo.persistence.MovieLocalDataSource
import com.fabiocati.aedo.persistence.internal.dao.MovieDao
import com.fabiocati.aedo.persistence.internal.mapper.toEntity
import com.fabiocati.aedo.persistence.internal.mapper.toMovie

internal class MovieLocalDataSourceImpl(
    private val movieDao: MovieDao
) : MovieLocalDataSource {

    override suspend fun getPopularMovies(): List<Movie> =
        movieDao.getMoviesByCategory(CATEGORY_POPULAR).map { it.toMovie() }

    override suspend fun getTrendingMovies(): List<Movie> =
        movieDao.getMoviesByCategory(CATEGORY_TRENDING).map { it.toMovie() }

    override suspend fun getStreamingServiceMovies(streamingService: StreamingService): List<Movie> =
        movieDao.getMoviesByCategory(getStreamingCategory(streamingService)).map { it.toMovie() }

    override suspend fun getSimilarMovies(movieId: Int): List<Movie> =
        movieDao.getMoviesByCategory(getSimilarCategory(movieId)).map { it.toMovie() }

    override suspend fun savePopularMovies(movies: List<Movie>) =
        movieDao.updateMoviesByCategory(CATEGORY_POPULAR, movies.map { it.toEntity() })

    override suspend fun saveTrendingMovies(movies: List<Movie>) =
        movieDao.updateMoviesByCategory(CATEGORY_TRENDING, movies.map { it.toEntity() })

    override suspend fun saveStreamingServiceMovies(streamingService: StreamingService, movies: List<Movie>) =
        movieDao.updateMoviesByCategory(getStreamingCategory(streamingService), movies.map { it.toEntity() })

    override suspend fun saveSimilarMovies(movieId: Int, movies: List<Movie>) =
        movieDao.updateMoviesByCategory(getSimilarCategory(movieId), movies.map { it.toEntity() })

    private fun getStreamingCategory(streamingService: StreamingService) = "streaming_${streamingService.name}"
    private fun getSimilarCategory(movieId: Int) = "similar_$movieId"

    companion object {
        private const val CATEGORY_POPULAR = "popular"
        private const val CATEGORY_TRENDING = "trending"
    }
}
