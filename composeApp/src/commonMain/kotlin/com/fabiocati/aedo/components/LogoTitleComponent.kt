package com.fabiocati.aedo.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun LogoTitleComponent(
    logoPath: String?,
    title: String,
    modifier: Modifier = Modifier
) {
    if (logoPath != null) {
        AsyncImage(
            model = logoPath,
            contentDescription = null,
            alignment = Alignment.BottomCenter,
            modifier = modifier.height(80.dp)

        )
    } else {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge.copy(Color.White),
            modifier = modifier
        )
    }
}