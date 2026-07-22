package com.meet.compose.loaders.ui.gallery

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.meet.compose.loaders.model.PixelPreset
import com.meet.compose.loaders.presets.PixelPresets

class GalleryViewModel : ViewModel() {
    var customActiveColor by mutableStateOf<Color?>(null)
    var selectedActiveColor by mutableStateOf<Color>(Color(0xFF6366F1))

    var customInactiveColor by mutableStateOf<Color?>(null)
    var selectedInactiveColor by mutableStateOf<Color>(Color.Unspecified) // Will be initialized by the view with outlineColor

    var customCanvasBgColor by mutableStateOf<Color?>(null)
    var selectedCanvasBgColor by mutableStateOf<Color>(Color.Unspecified) // Will be initialized by the view with defaultCanvasBg

    var globalSpeed by mutableStateOf(1.0f)
    var activeColorTarget by mutableStateOf<ColorPickerTarget?>(null)

    val presets: List<PixelPreset> = PixelPresets.all
}
