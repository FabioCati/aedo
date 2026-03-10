package com.fabiocati.aedo.persistence.internal.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import kotlin.time.Duration

@Entity(
    tableName = "movie_details",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
internal data class MovieDetailsEntity(
    @PrimaryKey val movieId: Int,
    val duration: Duration?,
    val yearOfProduction: LocalDate?,
)

@Entity(
    tableName = "movie_genres",
    primaryKeys = ["movieId", "genre"],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
internal data class MovieGenreEntity(
    val movieId: Int,
    val genre: String
)

@Entity(
    tableName = "movie_cast",
    primaryKeys = ["movieId", "name", "character"],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
internal data class MovieCastMemberEntity(
    val movieId: Int,
    val name: String,
    val character: String,
    val profilePath: String?
)

@Entity(
    tableName = "movie_crew",
    primaryKeys = ["movieId", "name"],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
internal data class MovieCrewMemberEntity(
    val movieId: Int,
    val name: String
)

@Entity(
    tableName = "movie_trailers",
    primaryKeys = ["movieId", "url"],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
internal data class MovieTrailerEntity(
    val movieId: Int,
    val url: String,
    val title: String,
    val thumbnail: String?
)

@Entity(
    tableName = "movie_languages",
    primaryKeys = ["movieId", "language"],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
internal data class MovieLanguageEntity(
    val movieId: Int,
    val language: String
)
