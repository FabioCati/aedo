package com.fabiocati.aedo.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fabiocati.aedo.AedoTheme
import com.fabiocati.aedo.components.FeaturedContentPager
import com.fabiocati.aedo.components.HomeScreenCategoryList
import com.fabiocati.aedo.models.fake.lordOfTheRings
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun HomeScreen(
    uiState: HomeScreenUiState,
    onMovieClicked: (movieId: Int) -> Unit,
    onSettingsClicked: () -> Unit,
) {
    val navInsets = WindowInsets.navigationBars.asPaddingValues()
    val statusBarInsets = WindowInsets.statusBars.asPaddingValues()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(
                bottom = navInsets.calculateBottomPadding()
            ),
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                Box(modifier = Modifier.fillMaxWidth().aspectRatio(2 / 3f)) {
                    FeaturedContentPager(
                        featuredElements = uiState.trendingMovies,
                        onMovieClicked = {
                            onMovieClicked(it.id)
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                    
                    IconButton(
                        onClick = onSettingsClicked,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = statusBarInsets.calculateTopPadding() + 8.dp, end = 8.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Black.copy(alpha = 0.3f),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
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
            onMovieClicked = {},
            onSettingsClicked = {}
        )
    }
}
