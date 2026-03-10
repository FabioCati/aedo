package com.fabiocati.aedo.persistence.internal.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
internal data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val logoPath: String?
)
