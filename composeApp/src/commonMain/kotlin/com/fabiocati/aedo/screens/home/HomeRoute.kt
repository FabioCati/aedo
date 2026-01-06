package com.fabiocati.aedo.screens.home

import androidx.compose.runtime.Composable

@Composable
fun HomeRoute(
    onNextPressed: () -> Unit,
) {
    HomeScreen(
        onNextPressed = onNextPressed,
    )
}