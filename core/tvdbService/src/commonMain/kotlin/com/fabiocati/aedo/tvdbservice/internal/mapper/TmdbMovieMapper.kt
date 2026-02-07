package com.fabiocati.aedo.tvdbservice.internal.mapper

import app.moviebase.tmdb.Tmdb3
import app.moviebase.tmdb.image.TmdbImageSize
import app.moviebase.tmdb.image.TmdbImageUrlBuilder
import app.moviebase.tmdb.model.TmdbMovie
import app.moviebase.tmdb.model.TmdbMovieDetail
import com.fabiocati.aedo.models.Movie
import com.fabiocati.aedo.models.MovieDetails
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

internal class TmdbMovieMapper(
    private val tmdb: Tmdb3
) {

    suspend fun toMovie(tmdbMovie: TmdbMovie): Movie {
        val logoImage = tmdb.movies.getImages(
            movieId =
                tmdbMovie.id,
            language = "en-US"
        ).logos.firstOrNull()
        return Movie(
            id = tmdbMovie.id,
            title = tmdbMovie.title,
            overview = tmdbMovie.overview,
            posterPath = tmdbMovie.posterPath?.let {
                TmdbImageUrlBuilder.build(
                    it,
                    TmdbImageSize.POSTER_W500
                )
            },
            backdropPath = tmdbMovie.backdropPath?.let {
                TmdbImageUrlBuilder.build(
                    it,
                    TmdbImageSize.ORIGINAL
                )
            },
            logoPath = logoImage?.let {
                TmdbImageUrlBuilder.build(
                    it.filePath,
                    TmdbImageSize.ORIGINAL
                )
            },
        )
    }

    fun toMovieDetails(tmdbMovie: TmdbMovieDetail): MovieDetails {
        return MovieDetails(
            id = tmdbMovie.id,
            title = tmdbMovie.title,
            overview = tmdbMovie.overview ?: "",
            posterPath = tmdbMovie.posterImage?.let {
                TmdbImageUrlBuilder.build(
                    it,
                    TmdbImageSize.ORIGINAL
                )
            },
            backdropPath = tmdbMovie.backdropImage?.let {
                TmdbImageUrlBuilder.build(
                    it,
                    TmdbImageSize.ORIGINAL
                )
            },
            logoPath = tmdbMovie.images?.logos?.firstOrNull()?.let {
                TmdbImageUrlBuilder.build(
                    it.filePath,
                    TmdbImageSize.ORIGINAL
                )
            },
            genres = tmdbMovie.genres.map { it.name },
            cast = tmdbMovie.credits?.cast?.map { it.name } ?: emptyList(),
            crew = tmdbMovie.credits?.crew?.map { it.name } ?: emptyList(),
            duration = tmdbMovie.runtime?.toDuration(DurationUnit.MINUTES),
            yearOfProduction = tmdbMovie.releaseDate,
            videos = tmdbMovie.videos?.results?.map { it.name ?: "" } ?: emptyList(),
            languages = listOf(tmdbMovie.originalLanguage ?: ""),
        )
    }
}