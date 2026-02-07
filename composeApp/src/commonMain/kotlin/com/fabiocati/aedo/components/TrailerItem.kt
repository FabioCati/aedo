package com.fabiocati.aedo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.fabiocati.aedo.AedoTheme
import com.fabiocati.aedo.models.Trailer
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TrailerItem(
    trailer: Trailer,
    onClick: (trailer: Trailer) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.padding(horizontal = 4.dp)
            .height(200.dp)
            .aspectRatio(4 / 3f)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.inverseOnSurface,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                onClick = {
                    onClick(trailer)
                }
            )
    ) {
        AsyncImage(
            model = trailer.thumbnail,
            contentScale = ContentScale.Crop,
            contentDescription = "",
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier.fillMaxSize().background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.2f),
                        Color.Black
                    )
                )
            )
        )
        Text(
            text = trailer.title,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)
        )
    }
}

@Preview
@Composable
private fun TrailerItemPreview() {
    val fakeTrailer = Trailer(
        url = "",
        title = "Trailer #1",
        thumbnail = ""
    )
    AedoTheme {
        TrailerItem(trailer = fakeTrailer, onClick = {})
    }
}