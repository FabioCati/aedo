package com.fabiocati.aedo.persistence.internal.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "movie_categories",
    primaryKeys = ["movieId", "category"],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
internal data class MovieCategoryEntity(
    val movieId: Int,
    val category: String
)
