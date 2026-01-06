package com.fabiocati.aedo.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.fabiocati.aedo.AedoTheme
import com.fabiocati.aedo.components.FeaturedContentPager
import com.fabiocati.aedo.model.mickey17Data
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun HomeScreen(
    onNextPressed: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        FeaturedContentPager(
            featuredElements = listOf(
                mickey17Data,
                mickey17Data
            )
        )
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