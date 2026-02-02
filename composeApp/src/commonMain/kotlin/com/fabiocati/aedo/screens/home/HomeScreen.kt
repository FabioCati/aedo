package com.fabiocati.aedo.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fabiocati.aedo.AedoTheme
import com.fabiocati.aedo.components.FeaturedContentPager
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun HomeScreen(
    uiState: HomeScreenUiState,
    onMovieClicked: (movieId: Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {

        FeaturedContentPager(
            featuredElements = uiState.featuredMovies,
            onMovieClicked = {
                onMovieClicked(it.id)
            }
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    val uiState = HomeScreenUiState()
    AedoTheme {
        HomeScreen(
            uiState = uiState,
            onMovieClicked = {}
        )
    }
}