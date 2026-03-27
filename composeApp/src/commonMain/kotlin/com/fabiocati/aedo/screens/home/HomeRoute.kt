package com.fabiocati.aedo.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeRoute(
    onMovieClicked: (Int) -> Unit,
    onSettingsClicked: () -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onMovieClicked = onMovieClicked,
        onSettingsClicked = onSettingsClicked,
    )
}