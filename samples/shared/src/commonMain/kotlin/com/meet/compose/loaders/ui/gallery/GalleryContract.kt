package com.meet.compose.loaders.ui.gallery

import androidx.compose.ui.graphics.Color
import com.meet.compose.loaders.model.PixelPreset
import com.meet.compose.loaders.presets.PixelPresets

data class GalleryUiState(
    val customActiveColor: Color? = null,
    val selectedActiveColor: Color = Color(0xFF6366F1),
    val customInactiveColor: Color? = null,
    val selectedInactiveColor: Color = Color.Unspecified,
    val customCanvasBgColor: Color? = null,
    val selectedCanvasBgColor: Color = Color.Unspecified,
    val globalSpeed: Float = 1.0f,
    val activeColorTarget: ColorPickerTarget? = null,
    val presets: List<PixelPreset> = PixelPresets.all
)

sealed interface GalleryIntent {
    data class SelectActiveColor(val color: Color) : GalleryIntent
    data class SelectInactiveColor(val color: Color) : GalleryIntent
    data class SelectCanvasBgColor(val color: Color) : GalleryIntent
    data class UpdateCustomActiveColor(val color: Color) : GalleryIntent
    data class UpdateCustomInactiveColor(val color: Color) : GalleryIntent
    data class UpdateCustomCanvasBgColor(val color: Color) : GalleryIntent
    data class UpdateGlobalSpeed(val speed: Float) : GalleryIntent
    data class OpenColorPicker(val target: ColorPickerTarget) : GalleryIntent
    object CloseColorPicker : GalleryIntent
    data class InitializeDefaultColors(val outlineColor: Color, val defaultCanvasBg: Color) : GalleryIntent
}
