package com.fabiocati.aedo.screens.home

import com.fabiocati.aedo.models.Movie

data class HomeScreenUiState(
    val featuredMovies: List<Movie> = emptyList(),
    val top10Movies: List<Movie> = emptyList(),
    val netflixMovies: List<Movie> = emptyList()
)
