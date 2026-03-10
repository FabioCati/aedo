package com.fabiocati.aedo.tvdbservice.internal.api

import app.moviebase.tmdb.Tmdb3
import app.moviebase.tmdb.discover.DiscoverCategory
import app.moviebase.tmdb.model.AppendResponse
import app.moviebase.tmdb.model.TmdbMediaType
import app.moviebase.tmdb.model.TmdbMovie
import app.moviebase.tmdb.model.TmdbTimeWindow
import arrow.core.Either
import com.fabiocati.aedo.models.Movie
import com.fabiocati.aedo.models.MovieDetails
import com.fabiocati.aedo.models.StreamingService
import com.fabiocati.aedo.tvdbservice.MoviesApi
import com.fabiocati.aedo.tvdbservice.internal.mapper.StreamingServiceMapper
import com.fabiocati.aedo.tvdbservice.internal.mapper.TmdbMovieMapper
import com.fabiocati.aedo.tvdbservice.internal.mapper.TmdbTrailerMapper
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

internal class MoviesApiImpl(
    private val tmdb: Tmdb3,
    private val mapper: TmdbMovieMapper,
    private val streamingServiceMapper: StreamingServiceMapper,
) : MoviesApi {

    override suspend fun getPopularMovies(page: Int): Either<Exception, List<Movie>> {
        val moviesResult = try {
            tmdb.movies.popular(
                page = page
            ).results
        } catch (e: Exception) {
            return Either.Left(e)
        }
        val movies = coroutineScope {
            moviesResult.filter {
                it.releaseDate?.let { releaseDate ->
                    releaseDate <= getToday()
                } ?: false
            }.map { movie ->
                async {
                    mapper.toMovie(movie)
                }
            }.awaitAll()
        }
        return Either.Right(movies)
    }

    override suspend fun getTrendingMovies(page: Int): Either<Exception, List<Movie>> {
        val moviesResult = try {
            tmdb.trending.getTrendingMovies(
                timeWindow = TmdbTimeWindow.WEEK,
                page = page
            ).results
        } catch (e: Exception) {
            return Either.Left(e)
        }
        val movies = coroutineScope {
            moviesResult.filter {
                it.releaseDate?.let { releaseDate ->
                    releaseDate <= getToday()
                } ?: false
            }.map { movie ->
                async {
                    mapper.toMovie(movie, getLogo = true)
                }
            }.awaitAll()
        }
        return Either.Right(movies)
    }

    override suspend fun getMovieDetails(movieId: Int): Either<Exception, MovieDetails> {
        return try {
            val detail = tmdb.movies.getDetails(
                movieId = movieId,
                language = "en-US",
                appendResponses = listOf(AppendResponse.IMAGES, AppendResponse.VIDEOS, AppendResponse.CREDITS)
            )
            Either.Right(mapper.toMovieDetails(detail))
        } catch (e: Exception) {
            Either.Left(e)
        }
    }

    override suspend fun getSimilarMovies(movieId: Int): Either<Exception, List<Movie>> {
        val moviesResult = try {
            tmdb.movies.getSimilar(movieId = movieId, page = 1).results
        } catch (e: Exception) {
            return Either.Left(e)
        }
        val movies = coroutineScope {
            moviesResult.map { movie ->
                async { mapper.toMovie(movie) }
            }.awaitAll()
        }
        return Either.Right(movies)
    }

    override suspend fun getStreamingServiceMovies(streamingService: StreamingService, page: Int): Either<Exception, List<Movie>> {
        val discoverCategory = streamingServiceMapper.toDiscoveryCategory(streamingService)
        return discoverMoviesByCategory(
            page = page,
            discoverCategory = discoverCategory
        )
    }

    private suspend fun discoverMoviesByCategory(page: Int, discoverCategory: DiscoverCategory): Either<Exception, List<Movie>> {
        val moviesResult = try {
            tmdb.discover.discoverByCategory(
                page = page,
                category = discoverCategory
            ).results.filterIsInstance<TmdbMovie>()
        } catch (e: Exception) {
            return Either.Left(e)
        }
        val movies = coroutineScope {
            moviesResult.filter {
                it.releaseDate?.let { releaseDate ->
                    releaseDate <= getToday()
                } ?: false
            }.map { movie ->
                async {
                    mapper.toMovie(movie)
                }
            }.awaitAll()
        }
        return Either.Right(movies)
    }

}

/**
 * Returns the current date as a LocalDate.
 */
private fun getToday(): LocalDate {
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
}