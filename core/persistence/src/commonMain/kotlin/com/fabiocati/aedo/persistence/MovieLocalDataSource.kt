package com.fabiocati.aedo.persistence

import com.fabiocati.aedo.models.Movie
import com.fabiocati.aedo.models.StreamingService

interface MovieLocalDataSource {
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getTrendingMovies(): List<Movie>
    suspend fun getStreamingServiceMovies(streamingService: StreamingService): List<Movie>
    suspend fun getSimilarMovies(movieId: Int): List<Movie>
    
    suspend fun savePopularMovies(movies: List<Movie>)
    suspend fun saveTrendingMovies(movies: List<Movie>)
    suspend fun saveStreamingServiceMovies(streamingService: StreamingService, movies: List<Movie>)
    suspend fun saveSimilarMovies(movieId: Int, movies: List<Movie>)
}
