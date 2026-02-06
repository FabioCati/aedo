package com.fabiocati.aedo.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
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
import androidx.compose.ui.unit.Dp
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
    indicatorWidth: Dp = 8.dp,
    indicatorHeight: Dp = 8.dp,
    spacing: Dp = 8.dp,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.width(105.dp)
    ) {

        val lazyListState = rememberLazyListState()

        val currentPage by derivedStateOf { pagerState.currentPage }
        LaunchedEffect(currentPage) {
            val lastElementVisible = lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
            val firstElementVisible = lazyListState.layoutInfo.visibleItemsInfo.firstOrNull()?.index ?: -1

            val nextElement = (currentPage + 2).coerceAtMost(pagerState.pageCount - 1)
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
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(count = pageCount) { iteration ->
                val isSelected = pagerState.currentPage == iteration
                val color = if (isSelected) activeColor else inactiveColor
                val width = if (isSelected) indicatorWidth * 2.5f else indicatorWidth

                val lastElementVisible = lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1

                val isSmall = shouldBeResized(
                    elementIndex = iteration,
                    lastElementVisible = lastElementVisible,
                    lazyListState = lazyListState,
                    pageCount = pageCount
                )

                val isMedium = shouldBeResized(
                    elementIndex = iteration - 1,
                    lastElementVisible = lastElementVisible,
                    lazyListState = lazyListState,
                    pageCount = pageCount
                ) || shouldBeResized(
                    elementIndex = iteration + 1,
                    lastElementVisible = lastElementVisible,
                    lazyListState = lazyListState,
                    pageCount = pageCount
                )


                val widthAnimation by animateDpAsState(width)

                val size = when {
                    isSmall -> Pair(4.dp, 4.dp)
                    isMedium -> Pair(6.dp, 6.dp)
                    else -> Pair(widthAnimation, 8.dp)
                }

                val heightAnimation by animateDpAsState(size.second)

                Box(
                    modifier = Modifier.size(width = widthAnimation, 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(color)
                            .size(
                                width = size.first,
                                height = heightAnimation
                            )
                    )
                }
            }
        }
    }
}

private fun shouldBeResized(elementIndex: Int, lastElementVisible: Int, lazyListState: LazyListState, pageCount: Int): Boolean {

    val isFirst = elementIndex == lazyListState.firstVisibleItemIndex && elementIndex != 0
    val isLast = (elementIndex == lastElementVisible && elementIndex != pageCount - 1)

    return isFirst || isLast
}

@Preview
@Composable
private fun HorizontalPagerIndicatorPreview() {
    AedoTheme {
        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f
        ) { 10 }

        Column {
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
