package com.meet.compose.loaders.ui.studio

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.meet.compose.loaders.model.PixelGridSize
import com.meet.compose.loaders.model.PixelShape

data class StudioDetailUiState(
    val selectedGridSize: PixelGridSize = PixelGridSize.Grid5x5,
    val customActiveColor: Color? = null,
    val selectedActiveColor: Color = Color(0xFF6366F1),
    val customInactiveColor: Color? = null,
    val selectedInactiveColor: Color = Color.Unspecified,
    val customCanvasBgColor: Color? = null,
    val selectedCanvasBgColor: Color = Color.Unspecified,
    val loaderSize: Dp = 48.dp,
    val speedMultiplier: Float = 1.0f,
    val selectedPixelShape: PixelShape = PixelShape.Circle,
    val isPlaying: Boolean = true,
    val exportFormat: ExportFormat = ExportFormat.KOTLIN_CODE,
    val activeColorTarget: ColorPickerTarget? = null,
    val isCopied: Boolean = false,
    val isDependencyCopied: Boolean = false
)

sealed interface StudioDetailIntent {
    data class SelectGridSize(val gridSize: PixelGridSize) : StudioDetailIntent
    data class SelectActiveColor(val color: Color) : StudioDetailIntent
    data class SelectInactiveColor(val color: Color) : StudioDetailIntent
    data class SelectCanvasBgColor(val color: Color) : StudioDetailIntent
    data class UpdateCustomActiveColor(val color: Color) : StudioDetailIntent
    data class UpdateCustomInactiveColor(val color: Color) : StudioDetailIntent
    data class UpdateCustomCanvasBgColor(val color: Color) : StudioDetailIntent
    data class UpdateLoaderSize(val size: Dp) : StudioDetailIntent
    data class UpdateSpeedMultiplier(val speed: Float) : StudioDetailIntent
    data class UpdatePixelShape(val shape: PixelShape) : StudioDetailIntent
    data class UpdateIsPlaying(val isPlaying: Boolean) : StudioDetailIntent
    data class UpdateExportFormat(val format: ExportFormat) : StudioDetailIntent
    data class OpenColorPicker(val target: ColorPickerTarget) : StudioDetailIntent
    object CloseColorPicker : StudioDetailIntent
    data class SetCopied(val isCopied: Boolean) : StudioDetailIntent
    data class SetDependencyCopied(val isCopied: Boolean) : StudioDetailIntent
    data class InitializeDefaultColors(val outlineColor: Color, val defaultCanvasBg: Color) : StudioDetailIntent
}
