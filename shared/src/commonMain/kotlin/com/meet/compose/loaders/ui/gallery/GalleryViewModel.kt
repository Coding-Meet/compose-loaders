package com.meet.compose.loaders.ui.gallery

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GalleryViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GalleryUiState())
    val uiState: StateFlow<GalleryUiState> = _uiState.asStateFlow()

    fun onIntent(intent: GalleryIntent) {
        when (intent) {
            is GalleryIntent.SelectActiveColor -> _uiState.update { it.copy(selectedActiveColor = intent.color) }
            is GalleryIntent.SelectInactiveColor -> _uiState.update { it.copy(selectedInactiveColor = intent.color) }
            is GalleryIntent.SelectCanvasBgColor -> _uiState.update { it.copy(selectedCanvasBgColor = intent.color) }
            is GalleryIntent.UpdateCustomActiveColor -> _uiState.update { it.copy(customActiveColor = intent.color, selectedActiveColor = intent.color) }
            is GalleryIntent.UpdateCustomInactiveColor -> _uiState.update { it.copy(customInactiveColor = intent.color, selectedInactiveColor = intent.color) }
            is GalleryIntent.UpdateCustomCanvasBgColor -> _uiState.update { it.copy(customCanvasBgColor = intent.color, selectedCanvasBgColor = intent.color) }
            is GalleryIntent.UpdateGlobalSpeed -> _uiState.update { it.copy(globalSpeed = intent.speed) }
            is GalleryIntent.OpenColorPicker -> _uiState.update { it.copy(activeColorTarget = intent.target) }
            is GalleryIntent.CloseColorPicker -> _uiState.update { it.copy(activeColorTarget = null) }
            is GalleryIntent.InitializeDefaultColors -> _uiState.update { state ->
                state.copy(
                    selectedInactiveColor = if (state.selectedInactiveColor == Color.Unspecified) intent.outlineColor else state.selectedInactiveColor,
                    selectedCanvasBgColor = if (state.selectedCanvasBgColor == Color.Unspecified) intent.defaultCanvasBg else state.selectedCanvasBgColor
                )
            }
        }
    }
}
