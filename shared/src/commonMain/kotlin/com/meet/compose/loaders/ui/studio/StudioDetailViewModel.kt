package com.meet.compose.loaders.ui.studio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.meet.compose.loaders.model.PixelGridSize
import com.meet.compose.loaders.model.PixelShape

class StudioDetailViewModel : ViewModel() {
    var selectedGridSize by mutableStateOf<PixelGridSize>(PixelGridSize.Grid5x5)

    var customActiveColor by mutableStateOf<Color?>(null)
    var selectedActiveColor by mutableStateOf<Color>(Color(0xFF6366F1))

    var customInactiveColor by mutableStateOf<Color?>(null)
    var selectedInactiveColor by mutableStateOf<Color>(Color.Unspecified) // Will be initialized by the view

    var customCanvasBgColor by mutableStateOf<Color?>(null)
    var selectedCanvasBgColor by mutableStateOf<Color>(Color.Unspecified) // Will be initialized by the view

    var loaderSize by mutableStateOf(48.dp)
    var speedMultiplier by mutableStateOf(1.0f)
    var selectedPixelShape by mutableStateOf(PixelShape.Circle)
    var isPlaying by mutableStateOf(true)
    var exportFormat by mutableStateOf(ExportFormat.KOTLIN_CODE)

    var activeColorTarget by mutableStateOf<ColorPickerTarget?>(null)

    var isCopied by mutableStateOf(false)
    var isDependencyCopied by mutableStateOf(false)
}
