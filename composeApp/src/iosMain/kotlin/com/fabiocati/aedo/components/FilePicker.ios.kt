package com.fabiocati.aedo.components

import androidx.compose.runtime.Composable

@Composable
actual fun FilePicker(
    show: Boolean,
    onFileSelected: (String?) -> Unit,
    onDismiss: () -> Unit
) {
    // LiteRT summarization is currently Android-only in this project
    if (show) {
        onDismiss()
    }
}
