package com.fabiocati.aedo.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fabiocati.aedo.components.FilePicker
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsRoute(
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    SettingsScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onFileSelected = { viewModel.setLiteRTModelPath(it) },
        onUseLiteRTChanged = { viewModel.setUseLiteRT(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    onBackClick: () -> Unit,
    onFileSelected: (String?) -> Unit,
    onUseLiteRTChanged: (Boolean) -> Unit
) {
    var showFilePicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Use On-Device LiteRT Summarizer",
                    style = MaterialTheme.typography.titleMedium
                )
                Switch(
                    checked = uiState.useLiteRT,
                    onCheckedChange = onUseLiteRTChanged
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Toggle to use a local LiteRT model instead of the Android Review Summarizer (Gemini API).",
                style = MaterialTheme.typography.bodyMedium
            )
            
            if (uiState.useLiteRT) {
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "LiteRT Model Configuration",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Select a local LiteRT model file (.litertlm) for review summarization.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Current Path:",
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = uiState.liteRTModelPath ?: "No model selected",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (uiState.liteRTModelPath == null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Button(
                    onClick = { showFilePicker = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Select Model File")
                }
            }
        }
    }

    FilePicker(
        show = showFilePicker,
        onFileSelected = {
            showFilePicker = false
            onFileSelected(it)
        },
        onDismiss = { showFilePicker = false }
    )
}
