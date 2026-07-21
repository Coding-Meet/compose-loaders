package com.meet.compose.loaders.export

import com.meet.compose.loaders.model.PixelGridSize
import com.meet.compose.loaders.model.PixelPreset

/**
 * Public code generator utility to format standalone Kotlin Compose code.
 */
object CodeExporter {

    fun generateCode(
        preset: PixelPreset,
        gridSize: PixelGridSize,
        sizeDp: Int = 48,
        speedMultiplier: Float = 1.0f
    ): String {
        val sizeName = when (gridSize) {
            PixelGridSize.Grid5x5 -> "Grid5x5"
            PixelGridSize.Grid7x7 -> "Grid7x7"
            is PixelGridSize.Custom -> "Custom(${gridSize.columns}, ${gridSize.rows})"
        }

        return """
PixelLoader(
    preset = PixelPresets.${preset.name},
    gridSize = PixelGridSize.$sizeName,
    modifier = Modifier.size(${sizeDp}.dp),
    speedMultiplier = ${speedMultiplier}f
)
        """.trimIndent()
    }
}
