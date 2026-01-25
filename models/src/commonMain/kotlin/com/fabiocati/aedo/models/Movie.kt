package com.fabiocati.aedo.models

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val logoPath: String?
)