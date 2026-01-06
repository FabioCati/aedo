package com.fabiocati.aedo.components

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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.fabiocati.aedo.AedoTheme
import com.fabiocati.aedo.model.HomeModel
import com.fabiocati.aedo.model.mickey17Data
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FeaturedContentPager(
    featuredElements: List<HomeModel>,
    modifier: Modifier = Modifier
) {

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { featuredElements.size }
    )

    HorizontalPager(
        state = pagerState,
        pageContent = { index ->
            val element = featuredElements[index]
            Box(
                modifier = Modifier.fillMaxWidth().aspectRatio(2 / 3f)
            ) {
                AsyncImage(
                    model = element.bannerUrl,
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
                    if (element.logoUrl != null) {
                        AsyncImage(
                            model = element.logoUrl,
                            contentDescription = null,
                            alignment = Alignment.BottomStart,
                            modifier = Modifier.fillMaxWidth(fraction = 0.8f)
                                .height(120.dp)
                        )
                    } else {
                        Text(
                            text = element.title
                        )
                    }
                    Spacer(
                        Modifier.height(16.dp)
                    )
                    Text(
                        text = element.shortDescription,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                    )
                    Spacer(
                        Modifier.height(8.dp)
                    )
                    Button(
                        onClick = { },
                        shape = MaterialTheme.shapes.large,
                    ) {
                        Text(
                            text = "More info",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }


            }
        },
        modifier = modifier
    )

}

@Preview
@Composable
private fun FeaturedContentPagerPreview() {
    AedoTheme {
        FeaturedContentPager(
            featuredElements = listOf(
                mickey17Data,
                mickey17Data
            )
        )
    }
}