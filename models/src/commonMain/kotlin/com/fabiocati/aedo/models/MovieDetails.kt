package com.fabiocati.aedo.models

import kotlin.time.Duration

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val logoPath: String?,
    val genres: List<String>,
    val cast: List<String>, // Cast
    val crew: List<String>,
    val duration: String,
    val yearOfProduction : String,
    val videos: List<String>,
    val languages: List<String>
)