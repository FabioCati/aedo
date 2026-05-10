package com.fabiocati.aedo.screens.settings

import androidx.lifecycle.ViewModel
import com.fabiocati.aedo.persistence.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SettingsUiState(
    val liteRTModelPath: String? = null,
    val useLiteRT: Boolean = false
)

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        SettingsUiState(
            liteRTModelPath = settingsRepository.getLiteRTModelPath(),
            useLiteRT = settingsRepository.getUseLiteRT()
        )
    )
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun setLiteRTModelPath(path: String?) {
        settingsRepository.setLiteRTModelPath(path)
        _uiState.update { it.copy(liteRTModelPath = path) }
    }

    fun setUseLiteRT(useLiteRT: Boolean) {
        settingsRepository.setUseLiteRT(useLiteRT)
        _uiState.update { it.copy(useLiteRT = useLiteRT) }
    }
}
