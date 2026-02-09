package com.fabiocati.aedo.screens.movieDetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fabiocati.aedo.models.Trailer
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MovieDetailsRoute(
    movieId: Int,
    viewModel: MovieDetailsViewModel = koinViewModel {
        parametersOf(movieId)
    },
    onTrailerClick: (trailer: Trailer) -> Unit,
    onMovieClick: (movieId: Int) -> Unit,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MovieDetailsScreen(
        uiState = uiState,
        onTrailerClick = onTrailerClick,
        onMovieClick = onMovieClick,
    )
}