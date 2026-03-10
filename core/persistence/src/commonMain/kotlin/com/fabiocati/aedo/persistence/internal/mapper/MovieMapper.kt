package com.fabiocati.aedo.persistence.internal.mapper

import com.fabiocati.aedo.models.*
import com.fabiocati.aedo.persistence.internal.entity.*

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

internal fun MovieDetailsCompositeEntity.toMovieDetails(): MovieDetails {
    return MovieDetails(
        id = movie.id,
        title = movie.title,
        overview = movie.overview,
        posterPath = movie.posterPath,
        backdropPath = movie.backdropPath,
        logoPath = movie.logoPath,
        genres = genres.map { it.genre },
        cast = cast.map { CastMember(it.name, it.character, it.profilePath) },
        crew = crew.map { it.name },
        duration = details?.duration,
        yearOfProduction = details?.yearOfProduction,
        trailers = trailers.map { Trailer(it.url, it.title, it.thumbnail) },
        languages = languages.map { it.language }
    )
}

internal fun MovieDetails.toDetailsEntity() = MovieDetailsEntity(id, duration, yearOfProduction)
internal fun MovieDetails.extractGenreEntities() = genres.map { MovieGenreEntity(id, it) }
internal fun MovieDetails.extractCastEntities() = cast.map { MovieCastMemberEntity(id, it.name, it.character, it.profilePath) }
internal fun MovieDetails.extractCrewEntities() = crew.map { MovieCrewMemberEntity(id, it) }
internal fun MovieDetails.extractTrailerEntities() = trailers.map { MovieTrailerEntity(id, it.url, it.title, it.thumbnail) }
internal fun MovieDetails.extractLanguageEntities() = languages.map { MovieLanguageEntity(id, it) }