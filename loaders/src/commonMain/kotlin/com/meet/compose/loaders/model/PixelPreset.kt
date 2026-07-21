package com.meet.compose.loaders.model

import androidx.compose.runtime.Immutable

/**
 * Represents a named pixel loader preset containing both 5x5 and 7x7 frame variants.
 *
 * @property id Unique identifier string for the preset.
 * @property name Display name for the animation preset.
 * @property grid5x5 5x5 grid pixel animation frame sequence.
 * @property grid7x7 7x7 grid pixel animation frame sequence.
 */
@Immutable
data class PixelPreset(
    val id: String,
    val name: String,
    val grid5x5: PixelAnimation,
    val grid7x7: PixelAnimation
) {
    /**
     * Resolves the [PixelAnimation] for the specified [gridSize].
     */
    fun selectAnimation(gridSize: PixelGridSize): PixelAnimation {
        return when (gridSize) {
            PixelGridSize.Grid5x5 -> grid5x5
            PixelGridSize.Grid7x7 -> grid7x7
            is PixelGridSize.Custom -> grid5x5
        }
    }
}
