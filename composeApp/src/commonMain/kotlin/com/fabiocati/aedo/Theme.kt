package com.fabiocati.aedo

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun AedoTheme(
    content: @Composable () -> Unit,
) {

    val colorScheme = getColorScheme(darkTheme = isSystemInDarkTheme())

    MaterialTheme(
        content = content,
        colorScheme = colorScheme
    )
}

@Composable
expect fun getColorScheme(darkTheme: Boolean): ColorScheme
