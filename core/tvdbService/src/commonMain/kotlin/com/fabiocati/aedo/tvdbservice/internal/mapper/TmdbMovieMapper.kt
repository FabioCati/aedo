package com.fabiocati.aedo.tvdbservice.internal.mapper

import app.moviebase.tmdb.Tmdb3
import app.moviebase.tmdb.image.TmdbImageSize
import app.moviebase.tmdb.image.TmdbImageUrlBuilder
import app.moviebase.tmdb.model.TmdbMovie
import com.fabiocati.aedo.models.Movie
import com.fabiocati.aedo.tvdbservice.internal.Secrets

internal class TmdbMovieMapper(
    private val tmdb: Tmdb3 = Tmdb3(Secrets.TMDB_API_KEY)
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
            posterPath = tmdbMovie.posterPath,
            logoPath = logoImage?.let {
                TmdbImageUrlBuilder.build(
                    it.filePath,
                    TmdbImageSize.LOGO_W300
                )
            },
        )
    }
}