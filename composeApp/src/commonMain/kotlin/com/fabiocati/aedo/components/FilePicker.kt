package com.fabiocati.aedo.components

import androidx.compose.runtime.Composable

@Composable
expect fun FilePicker(
    show: Boolean,
    onFileSelected: (String?) -> Unit,
    onDismiss: () -> Unit
)
