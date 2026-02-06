package com.fabiocati.aedo.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fabiocati.aedo.AedoTheme
import com.fabiocati.aedo.components.FeaturedContentPager
import com.fabiocati.aedo.components.HomeScreenCategoryList
import com.fabiocati.aedo.models.fake.lordOfTheRings
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun HomeScreen(
    uiState: HomeScreenUiState,
    onMovieClicked: (movieId: Int) -> Unit,
) {
    val windowInsets = WindowInsets.navigationBars.asPaddingValues()

    LazyColumn(
        contentPadding = PaddingValues(bottom = windowInsets.calculateBottomPadding()),
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        item {
            FeaturedContentPager(
                featuredElements = uiState.trendingMovies,
                onMovieClicked = {
                    onMovieClicked(it.id)
                },
                modifier = Modifier.fillMaxWidth().aspectRatio(2 / 3f)
            )
        }

        item {
            HomeScreenCategoryList(
                categoryName = "Popolari",
                movies = uiState.popularMovies,
                onMovieClicked = {
                    onMovieClicked(it.id)
                }
            )
        }
        item {
            HomeScreenCategoryList(
                categoryName = "Su Netflix",
                movies = uiState.netflixMovies,
                onMovieClicked = {
                    onMovieClicked(it.id)
                }
            )
        }
        item {
            HomeScreenCategoryList(
                categoryName = "Su Disney+",
                movies = uiState.disneyPlusMovies,
                onMovieClicked = {
                    onMovieClicked(it.id)
                }
            )
        }
        item {
            HomeScreenCategoryList(
                categoryName = "Su Apple TV",
                movies = uiState.appleTvMovies,
                onMovieClicked = {
                    onMovieClicked(it.id)
                }
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    val uiState = HomeScreenUiState(
        trendingMovies = listOf(lordOfTheRings, lordOfTheRings),
        popularMovies = listOf(lordOfTheRings, lordOfTheRings, lordOfTheRings, lordOfTheRings),
    )
    AedoTheme {
        HomeScreen(
            uiState = uiState,
            onMovieClicked = {}
        )
    }
}