package com.fabiocati.aedo.screens.movieDetails

import com.fabiocati.aedo.models.Movie
import com.fabiocati.aedo.models.MovieDetails

data class MovieDetailsScreenUiState(
    val movieDetails: MovieDetails? = null,
    val similarMovies: List<Movie> = emptyList(),
)
