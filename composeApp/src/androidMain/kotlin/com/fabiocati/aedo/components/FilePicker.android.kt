package com.fabiocati.aedo.components

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@Composable
actual fun FilePicker(
    show: Boolean,
    onFileSelected: (String?) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                // Perform copy in a background thread if possible, 
                // but for simplicity in this component we'll call a helper.
                // In a production app, showing a progress indicator would be better.
                val path = copyFileToInternalStorage(context, uri)
                onFileSelected(path)
            } else {
                onDismiss()
            }
        }
    )

    LaunchedEffect(show) {
        if (show) {
            launcher.launch(arrayOf("*/*"))
        }
    }
}

private fun copyFileToInternalStorage(context: Context, uri: Uri): String? {
    val contentResolver = context.contentResolver
    val fileName = getFileName(context, uri) ?: "model.litertlm"
    val file = File(context.filesDir, fileName)

    return try {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

private fun getFileName(context: Context, uri: Uri): String? {
    var result: String? = null
    if (uri.scheme == "content") {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (index != -1) {
                    result = it.getString(index)
                }
            }
        }
    }
    if (result == null) {
        result = uri.path
        val cut = result?.lastIndexOf('/')
        if (cut != null && cut != -1) {
            result = result?.substring(cut + 1)
        }
    }
    return result
}
