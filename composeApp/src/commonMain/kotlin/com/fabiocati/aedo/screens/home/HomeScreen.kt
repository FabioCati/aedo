package com.fabiocati.aedo.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.fabiocati.aedo.AedoTheme
import com.fabiocati.aedo.components.FeaturedContentPager
import com.fabiocati.aedo.models.fake.lordOfTheRings
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun HomeScreen(
    uiState: HomeScreenUiState,
    onMovieClicked: (movieId: Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        item {
            FeaturedContentPager(
                featuredElements = uiState.featuredMovies,
                onMovieClicked = {
                    onMovieClicked(it.id)
                }
            )
        }

        item {
            Column {
                Text(
                    text = "Nuove uscite",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(16.dp)
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(items = uiState.netflixMovies) { movie ->
                        AsyncImage(
                            model = movie.posterPath,
                            contentScale = ContentScale.Crop,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .height(170.dp)
                                .width(120.dp)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.inverseOnSurface,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clip(
                                    shape = RoundedCornerShape(12.dp)
                                )
                        )
                    }
                }
            }
        }

    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    val uiState = HomeScreenUiState(
        featuredMovies = listOf(lordOfTheRings, lordOfTheRings),
        netflixMovies = listOf(lordOfTheRings, lordOfTheRings, lordOfTheRings, lordOfTheRings),
    )
    AedoTheme {
        HomeScreen(
            uiState = uiState,
            onMovieClicked = {}
        )
    }
}