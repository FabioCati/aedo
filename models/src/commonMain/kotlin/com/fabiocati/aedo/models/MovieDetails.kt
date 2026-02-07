package com.fabiocati.aedo.models

import kotlinx.datetime.LocalDate
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
    val duration: Duration?,
    val yearOfProduction : LocalDate?,
    val trailers: List<Trailer>,
    val languages: List<String>
)