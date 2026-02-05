package com.fabiocati.aedo.screens.home

import com.fabiocati.aedo.models.Movie

data class HomeScreenUiState(
    val trendingMovies: List<Movie> = emptyList(),
    val popularMovies: List<Movie> = emptyList(),
    val netflixMovies: List<Movie> = emptyList(),
    val appleTvMovies: List<Movie> = emptyList(),
    val disneyPlusMovies: List<Movie> = emptyList(),
    val primeMovies: List<Movie> = emptyList()
)
