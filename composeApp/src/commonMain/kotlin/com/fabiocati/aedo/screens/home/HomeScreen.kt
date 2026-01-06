package com.fabiocati.aedo.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.fabiocati.aedo.AedoTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun HomeScreen(
    onNextPressed: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
    ) {
        Button(onClick = onNextPressed) {
            Text("Next")
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    AedoTheme {
        HomeScreen(
            onNextPressed = {}
        )
    }
}