package com.fabiocati.aedo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.fabiocati.aedo.AedoTheme
import com.fabiocati.aedo.models.CastMember
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CastItem(
    castMember: CastMember,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(horizontal = 4.dp)
            .width(100.dp)
    ) {
        AsyncImage(
            model = castMember.profilePath,
            contentScale = ContentScale.Crop,
            contentDescription = castMember.name,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = castMember.name,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = castMember.character,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
private fun CastItemPreview() {
    val fakeCastMember = CastMember(
        name = "Elijah Wood",
        character = "Frodo Baggins",
        profilePath = null,
    )
    AedoTheme {
        CastItem(castMember = fakeCastMember)
    }
}