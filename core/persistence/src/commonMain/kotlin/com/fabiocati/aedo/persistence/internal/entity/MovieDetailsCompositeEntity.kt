package com.fabiocati.aedo.persistence.internal.entity

import androidx.room.Embedded
import androidx.room.Relation

internal data class MovieDetailsCompositeEntity(
    @Embedded val movie: MovieEntity,
    
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    )
    val details: MovieDetailsEntity?,

    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    )
    val genres: List<MovieGenreEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    )
    val cast: List<MovieCastMemberEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    )
    val crew: List<MovieCrewMemberEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    )
    val trailers: List<MovieTrailerEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    )
    val languages: List<MovieLanguageEntity>
)
