package com.fabiocati.aedo.screens.movieDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun MovieDetailsScreen(
    uiState: MovieDetailsScreenUiState,
) {
    Column {
        Text(text = uiState.movieDetails?.title ?: "")
        Text(text = uiState.movieDetails?.overview ?: "")

        AsyncImage(
            model = uiState.movieDetails?.posterPath,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
    }
}