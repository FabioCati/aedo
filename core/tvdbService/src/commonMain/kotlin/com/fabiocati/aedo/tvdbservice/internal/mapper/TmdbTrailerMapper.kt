package com.fabiocati.aedo.tvdbservice.internal.mapper

import app.moviebase.tmdb.image.TmdbImageUrlBuilder
import app.moviebase.tmdb.model.TmdbVideo
import com.fabiocati.aedo.models.Trailer

internal class TmdbTrailerMapper() {

    private val imageBuilder = TmdbImageUrlBuilder

    fun toTrailer(tmdbVideo: TmdbVideo): Trailer {
        val thumbnail = imageBuilder.buildYoutube(
            video = tmdbVideo,
            width = 700
        )
        return Trailer(
            url = "https://www.youtube.com/watch?v=${tmdbVideo.key}",
            title = tmdbVideo.name ?: "",
            thumbnail = thumbnail
        )
    }
}