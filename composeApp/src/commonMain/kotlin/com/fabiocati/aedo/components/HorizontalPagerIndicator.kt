package com.fabiocati.aedo.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fabiocati.aedo.AedoTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HorizontalPagerIndicator(
    pagerState: PagerState,
    pageCount: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = Color.White,
    inactiveColor: Color = activeColor.copy(alpha = 0.5f),
) {
    val lazyListState = rememberLazyListState()

    val currentPage by derivedStateOf { pagerState.currentPage }
    LaunchedEffect(currentPage) {
        val lastElementVisible = lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
        val firstElementVisible = lazyListState.layoutInfo.visibleItemsInfo.firstOrNull()?.index ?: -1

        val nextElement = (currentPage + 2).coerceAtMost(pagerState.pageCount)
        if (lastElementVisible < nextElement) {
            lazyListState.animateScrollToItem(firstElementVisible + 1)
        }

        val previousElement = (currentPage - 2).coerceAtLeast(0)
        if (firstElementVisible > previousElement) {
            lazyListState.animateScrollToItem(previousElement)
        }
    }


    LazyRow(
        state = lazyListState,
        userScrollEnabled = false,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.width(124.dp)
    ) {
        items(count = pageCount) { iteration ->
            val isSelected = pagerState.currentPage == iteration
            val color = if (isSelected) activeColor else inactiveColor
            val width = if (isSelected) 8.dp * 2.5f else 8.dp
            val widthAnimation by animateDpAsState(width)
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(widthAnimation)
                        .height(8.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }

        }
    }
}

@Preview
@Composable
private fun HorizontalPagerIndicatorPreview() {
    AedoTheme {
        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f
        ) { 10 }

        Column(
            modifier = Modifier.width(84.dp)
        ) {
            HorizontalPagerIndicator(
                pagerState = pagerState,
                pageCount = 10,
            )

            Button(
                onClick = {
                    pagerState.requestScrollToPage(
                        page = (pagerState.currentPage + 1) % 10
                    )
                }
            ) {
                Text("next")
            }
        }

    }
}
