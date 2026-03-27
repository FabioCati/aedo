package com.fabiocati.aedo.screens.movieDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.fabiocati.aedo.AedoTheme
import com.fabiocati.aedo.components.CastItem
import com.fabiocati.aedo.components.LogoTitleComponent
import com.fabiocati.aedo.components.MovieItem
import com.fabiocati.aedo.components.ReviewItem
import com.fabiocati.aedo.components.TrailerItem
import com.fabiocati.aedo.models.Trailer
import com.fabiocati.aedo.models.fake.lordOfTheRingsDetails
import com.fabiocati.aedo.summarizer.ReviewSummarizer
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Duration
import kotlin.time.DurationUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    uiState: MovieDetailsScreenUiState,
    onTrailerClick: (trailer: Trailer) -> Unit,
    onMovieClick: (movieId: Int) -> Unit = {},
    onBackClick: () -> Unit = {},
    onSummarizeClick: () -> Unit = {},
    onDownloadClick: () -> Unit = {},
    onDismissSummary: () -> Unit = {},
) {
    val movie = uiState.movieDetails
    val listState = rememberLazyListState()

    val topBarAlpha by remember {
        derivedStateOf {
            when {
                listState.firstVisibleItemIndex > 0 -> 1f
                else -> {
                    val scrollOffset = listState.firstVisibleItemScrollOffset.toFloat()
                    (scrollOffset / 600f).coerceIn(0f, 1f)
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()),
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().aspectRatio(1.7f / 3f)
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
                Column {
                    Text(
                        text = "Trailers",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(16.dp)
                    )
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(movie?.trailers ?: emptyList()) { trailer ->
                            TrailerItem(
                                trailer = trailer,
                                onClick = onTrailerClick,
                            )
                        }
                    }
                }
            }
            item {
                Column {
                    Text(
                        text = "Cast & Crew",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(16.dp)
                    )
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(movie?.cast ?: emptyList()) { castMember ->
                            CastItem(castMember = castMember)
                        }
                    }
                }
            }
            if (!movie?.reviews.isNullOrEmpty()) {
                val reviews = movie.reviews
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Reviews",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        SummarizeButton(
                            status = uiState.summarizerStatus,
                            onSummarizeClick = onSummarizeClick,
                            onDownloadClick = onDownloadClick
                        )
                    }
                }
                items(reviews) { review ->
                    ReviewItem(review = review)
                }
            }
            if (uiState.similarMovies.isNotEmpty()) {
                item {
                    Column(modifier = Modifier.padding(bottom = 12.dp)) {
                        Text(
                            text = "Correlated",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(16.dp)
                        )
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) {
                            items(uiState.similarMovies) { movie ->
                                MovieItem(
                                    movie = movie,
                                    onMovieClicked = {
                                        onMovieClick(it.id)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        TopAppBar(
            title = {
                Text(
                    text = movie?.title ?: "",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = topBarAlpha),
                )
            },
            navigationIcon = {
                val arrowColor = if (topBarAlpha > 0.5f) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    Color.White
                }
                IconButton(
                    onClick = onBackClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Black.copy(alpha = 0.3f * (1f - topBarAlpha)),
                    ),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = arrowColor,
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background.copy(alpha = topBarAlpha),
            ),
            windowInsets = WindowInsets.statusBars,
        )

        if (uiState.summaryResult != SummaryResult.Idle) {
            ModalBottomSheet(
                onDismissRequest = onDismissSummary,
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                SummarySheetContent(uiState.summaryResult)
            }
        }
    }
}

@Composable
fun SummarizeButton(
    status: ReviewSummarizer.Status,
    onSummarizeClick: () -> Unit,
    onDownloadClick: () -> Unit
) {
    when (status) {
        ReviewSummarizer.Status.NotAvailable -> {
            Button(
                onClick = onDownloadClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("AI Summarizer", style = MaterialTheme.typography.labelLarge)
            }
        }
        ReviewSummarizer.Status.Downloading -> {
            Button(
                onClick = {},
                enabled = false,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
            ) {
                CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                Spacer(Modifier.width(8.dp))
                Text("Downloading model...", style = MaterialTheme.typography.labelLarge)
            }
        }
        is ReviewSummarizer.Status.Available -> {
            Button(
                onClick = onSummarizeClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Summarize with AI", style = MaterialTheme.typography.labelLarge)
            }
        }
        is ReviewSummarizer.Status.Error -> {
            Text(text = "AI Error - ${status.message}", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
fun SummarySheetContent(result: SummaryResult) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.AutoAwesome,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = "AI Review Summary",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(Modifier.height(24.dp))
        
        when (result) {
            SummaryResult.Loading -> {
                Column(
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Spacer(Modifier.height(16.dp))
                    Text("AI is reading reviews...", style = MaterialTheme.typography.bodyMedium)
                }
            }
            is SummaryResult.Success -> {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = result.summary,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp),
                        lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.2f
                    )
                }
            }
            is SummaryResult.Error -> {
                Text(
                    text = result.message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            SummaryResult.Idle -> {}
        }
        
        Spacer(Modifier.height(32.dp))
        
        Text(
            text = "Generated with Google AI Core",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        
        Spacer(Modifier.height(16.dp))
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
        MovieDetailsScreen(
            uiState = uiState,
            onTrailerClick = {}
        )
    }
}
