package com.fabiocati.aedo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.fabiocati.aedo.AedoTheme
import com.fabiocati.aedo.models.Movie
import com.fabiocati.aedo.models.fake.lordOfTheRings
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FeaturedContentPager(
    featuredElements: List<Movie>,
    onMovieClicked: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { featuredElements.size }
    )

    Box(
        modifier = modifier,
    ) {
        HorizontalPager(
            state = pagerState,
            pageContent = { index ->
                val element = featuredElements[index]
                Box(
                    modifier = Modifier.fillMaxWidth().aspectRatio(2 / 3f)
                ) {
                    AsyncImage(
                        model = element.backdropPath,
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
                                    Color.Black.copy(alpha = 0.3f),
                                    Color.Black.copy(alpha = 0.5f)
                                ),
                            )
                        )
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(bottom = 32.dp)
                            .padding(horizontal = 16.dp)
                    ) {
                        if (element.logoPath != null) {
                            AsyncImage(
                                model = element.logoPath,
                                contentDescription = null,
                                alignment = Alignment.BottomCenter,
                                modifier = Modifier
                                    .fillMaxWidth(fraction = 0.8f)
                                    .align(Alignment.CenterHorizontally)
                                    .height(80.dp)

                            )
                        } else {
                            Text(
                                text = element.title,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineLarge.copy(Color.White),
                                modifier = Modifier
                                    .fillMaxWidth(fraction = 0.8f)
                                    .align(Alignment.CenterHorizontally)
                            )
                        }
                        Spacer(
                            Modifier.height(16.dp)
                        )
                        Text(
                            text = element.overview,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(
                            Modifier.height(8.dp)
                        )
                        Button(
                            onClick = { onMovieClicked(element) },
                            shape = MaterialTheme.shapes.large,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text(
                                text = "More info",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }

                }
            },
        )
        HorizontalPagerIndicator(
            pagerState = pagerState,
            pageCount = featuredElements.size,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 12.dp)
        )
    }

}

@Preview
@Composable
private fun FeaturedContentPagerPreview() {
    AedoTheme {
        FeaturedContentPager(
            featuredElements = listOf(lordOfTheRings),
            onMovieClicked = {}
        )
    }
}