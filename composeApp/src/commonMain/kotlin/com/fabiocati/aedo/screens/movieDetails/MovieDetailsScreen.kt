package com.fabiocati.aedo.screens.movieDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.fabiocati.aedo.AedoTheme
import com.fabiocati.aedo.components.LogoTitleComponent
import com.fabiocati.aedo.models.fake.lordOfTheRingsDetails
import kotlinx.datetime.toDateTimePeriod
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Duration
import kotlin.time.DurationUnit

@Composable
fun MovieDetailsScreen(
    uiState: MovieDetailsScreenUiState,
) {
    val movie = uiState.movieDetails
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().aspectRatio(1.6f / 3f)
                ) {
                    AsyncImage(
                        model = movie?.backdropPath,
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier.fillMaxSize().background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.5f),
                                    Color.Black.copy(alpha = 0.8f)
                                ),
                            )
                        )
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                    ) {
                        LogoTitleComponent(
                            logoPath = movie?.logoPath,
                            title = movie?.title ?: "",
                            modifier = Modifier
                                .fillMaxWidth(fraction = 0.8f)
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = buildString {
                                if (movie == null) return@buildString
                                movie.genres.forEachIndexed { index, text ->
                                    append(text)
                                    if (index == movie.genres.lastIndex) return@forEachIndexed
                                    append(" • ")
                                }
                            },
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = buildString {
                                if (movie == null) return@buildString
                                append(movie.yearOfProduction?.year.toString())
                                append(" ● ")
                                append(movie.duration?.toMovieFormat() ?: "")
                            },
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = movie?.overview ?: "",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
            item {

            }
        }
    }
}

private fun Duration.toMovieFormat(): String {
    return buildString {
        val durationHours = this@toMovieFormat.toDouble(DurationUnit.HOURS).toInt()
        val durationMinutes = (this@toMovieFormat.toDouble(DurationUnit.MINUTES) % 60).toInt()
        if (durationHours > 0) {
            append(durationHours)
            append(" h ")
        }
        append(durationMinutes)
        append(" min")
    }
}

@Preview
@Composable
fun MovieDetailsScreenPreview() {
    val uiState = MovieDetailsScreenUiState(
        movieDetails = lordOfTheRingsDetails
    )
    AedoTheme {
        MovieDetailsScreen(uiState = uiState)
    }
}