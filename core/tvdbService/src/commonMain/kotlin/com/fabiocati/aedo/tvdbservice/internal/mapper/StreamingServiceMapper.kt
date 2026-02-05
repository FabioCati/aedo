package com.fabiocati.aedo.tvdbservice.internal.mapper

import app.moviebase.tmdb.discover.DiscoverCategory
import app.moviebase.tmdb.model.TmdbMediaType
import com.fabiocati.aedo.models.StreamingService

internal class StreamingServiceMapper {

    fun toDiscoveryCategory(service: StreamingService): DiscoverCategory {
        val region = "US"
        return when (service) {
            StreamingService.NETFLIX -> DiscoverCategory.OnStreaming.Netflix(
                mediaType = TmdbMediaType.MOVIE,
                watchRegion = region
            )

            StreamingService.DISNEY_PLUS -> DiscoverCategory.OnStreaming.DisneyPlus(
                mediaType = TmdbMediaType.MOVIE,
                watchRegion = region
            )

            StreamingService.AMAZON_PRIME -> DiscoverCategory.OnStreaming.AmazonPrimeVideo(
                mediaType = TmdbMediaType.MOVIE,
                watchRegion = region
            )

            StreamingService.APPLE_TV -> DiscoverCategory.OnStreaming.AppleTv(
                mediaType = TmdbMediaType.MOVIE,
                watchRegion = region
            )
        }
    }
}