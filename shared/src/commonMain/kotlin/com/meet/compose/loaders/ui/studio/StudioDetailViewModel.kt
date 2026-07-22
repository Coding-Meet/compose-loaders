package com.meet.compose.loaders.ui.studio

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class StudioDetailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(StudioDetailUiState())
    val uiState: StateFlow<StudioDetailUiState> = _uiState.asStateFlow()

    fun onIntent(intent: StudioDetailIntent) {
        when (intent) {
            is StudioDetailIntent.SelectGridSize -> _uiState.update { it.copy(selectedGridSize = intent.gridSize, isCopied = false) }
            is StudioDetailIntent.SelectActiveColor -> _uiState.update { it.copy(selectedActiveColor = intent.color, isCopied = false) }
            is StudioDetailIntent.SelectInactiveColor -> _uiState.update { it.copy(selectedInactiveColor = intent.color, isCopied = false) }
            is StudioDetailIntent.SelectCanvasBgColor -> _uiState.update { it.copy(selectedCanvasBgColor = intent.color, isCopied = false) }
            is StudioDetailIntent.UpdateCustomActiveColor -> _uiState.update { it.copy(customActiveColor = intent.color, selectedActiveColor = intent.color, isCopied = false) }
            is StudioDetailIntent.UpdateCustomInactiveColor -> _uiState.update { it.copy(customInactiveColor = intent.color, selectedInactiveColor = intent.color, isCopied = false) }
            is StudioDetailIntent.UpdateCustomCanvasBgColor -> _uiState.update { it.copy(customCanvasBgColor = intent.color, selectedCanvasBgColor = intent.color, isCopied = false) }
            is StudioDetailIntent.UpdateLoaderSize -> _uiState.update { it.copy(loaderSize = intent.size, isCopied = false) }
            is StudioDetailIntent.UpdateSpeedMultiplier -> _uiState.update { it.copy(speedMultiplier = intent.speed, isCopied = false) }
            is StudioDetailIntent.UpdatePixelShape -> _uiState.update { it.copy(selectedPixelShape = intent.shape, isCopied = false) }
            is StudioDetailIntent.UpdateIsPlaying -> _uiState.update { it.copy(isPlaying = intent.isPlaying) }
            is StudioDetailIntent.UpdateExportFormat -> _uiState.update { it.copy(exportFormat = intent.format, isCopied = false) }
            is StudioDetailIntent.OpenColorPicker -> _uiState.update { it.copy(activeColorTarget = intent.target) }
            is StudioDetailIntent.CloseColorPicker -> _uiState.update { it.copy(activeColorTarget = null) }
            is StudioDetailIntent.SetCopied -> _uiState.update { it.copy(isCopied = intent.isCopied) }
            is StudioDetailIntent.SetDependencyCopied -> _uiState.update { it.copy(isDependencyCopied = intent.isCopied) }
            is StudioDetailIntent.InitializeDefaultColors -> _uiState.update { state ->
                state.copy(
                    selectedInactiveColor = if (state.selectedInactiveColor == Color.Unspecified) intent.outlineColor else state.selectedInactiveColor,
                    selectedCanvasBgColor = if (state.selectedCanvasBgColor == Color.Unspecified) intent.defaultCanvasBg else state.selectedCanvasBgColor
                )
            }
        }
    }
}
