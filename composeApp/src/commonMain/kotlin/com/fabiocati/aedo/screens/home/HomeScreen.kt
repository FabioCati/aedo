package com.fabiocati.aedo.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import arrow.core.Either
import com.fabiocati.aedo.AedoTheme
import com.fabiocati.aedo.components.FeaturedContentPager
import com.fabiocati.aedo.models.Movie
import com.fabiocati.aedo.tvdbservice.MoviesApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.getKoin

@Composable
internal fun HomeScreen(
    onNextPressed: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {

        var elements by remember { mutableStateOf(emptyList<Movie>()) }
        val koin = getKoin()
        LaunchedEffect(true){
            val api = koin.get<MoviesApi>(MoviesApi::class)
            val movies = api.getPopularMovies(1) as Either.Right
            elements = movies.value
        }

        FeaturedContentPager(
            featuredElements = elements
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    AedoTheme {
        HomeScreen(
            onNextPressed = {}
        )
    }
}