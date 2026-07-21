package com.meet.compose.loaders.model

import androidx.compose.runtime.Immutable

/**
 * Dimension specification for a pixel loading animation grid.
 */
@Immutable
sealed interface PixelGridSize {
    val columns: Int
    val rows: Int

    /**
     * Standard 5x5 dot matrix grid.
     */
    data object Grid5x5 : PixelGridSize {
        override val columns: Int = 5
        override val rows: Int = 5
    }

    /**
     * Standard 7x7 dot matrix grid.
     */
    data object Grid7x7 : PixelGridSize {
        override val columns: Int = 7
        override val rows: Int = 7
    }

    /**
     * Custom matrix grid dimension.
     */
    data class Custom(
        override val columns: Int,
        override val rows: Int
    ) : PixelGridSize
}
