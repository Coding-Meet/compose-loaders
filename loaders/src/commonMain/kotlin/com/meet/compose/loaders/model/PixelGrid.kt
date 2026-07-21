package com.meet.compose.loaders.model

import androidx.compose.runtime.Immutable

/**
 * Immutable 2D dot matrix grid representing active and inactive pixel states.
 *
 * @property columns Total horizontal pixel count.
 * @property rows Total vertical pixel count.
 * @property pixels Flattened boolean matrix array of size columns * rows.
 */
@Immutable
class PixelGrid(
    val columns: Int,
    val rows: Int,
    private val pixels: BooleanArray
) {
    /**
     * Returns true if the pixel at index [index] is active.
     */
    fun isPixelActive(index: Int): Boolean {
        return if (index in pixels.indices) pixels[index] else false
    }

    /**
     * Returns true if the pixel at column [col] and row [row] is active.
     */
    fun isPixelActive(col: Int, row: Int): Boolean {
        return isPixelActive(row * columns + col)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PixelGrid) return false
        if (columns != other.columns || rows != other.rows) return false
        return pixels.contentEquals(other.pixels)
    }

    override fun hashCode(): Int {
        var result = columns
        result = 31 * result + rows
        result = 31 * result + pixels.contentHashCode()
        return result
    }
}
