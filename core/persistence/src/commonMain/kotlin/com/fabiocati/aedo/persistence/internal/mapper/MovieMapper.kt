package com.fabiocati.aedo.persistence.internal.mapper

import com.fabiocati.aedo.models.Movie
import com.fabiocati.aedo.persistence.internal.entity.MovieEntity

internal fun MovieEntity.toMovie() = Movie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    logoPath = logoPath
)

internal fun Movie.toEntity() = MovieEntity(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    logoPath = logoPath
)
