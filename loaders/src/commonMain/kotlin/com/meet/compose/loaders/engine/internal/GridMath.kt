package com.meet.compose.loaders.engine.internal

import androidx.compose.ui.geometry.Offset

/**
 * Internal grid geometry and layout math helper.
 */
internal object GridMath {

    fun calculateDotRadius(
        width: Float,
        height: Float,
        columns: Int,
        rows: Int,
        gapFraction: Float = 0.25f
    ): Float {
        val cellWidth = width / columns
        val cellHeight = height / rows
        val cellSize = minOf(cellWidth, cellHeight)
        return (cellSize * (1f - gapFraction)) / 2f
    }

    fun calculateCenterOffset(
        col: Int,
        row: Int,
        width: Float,
        height: Float,
        columns: Int,
        rows: Int
    ): Offset {
        val cellWidth = width / columns
        val cellHeight = height / rows
        val x = (col + 0.5f) * cellWidth
        val y = (row + 0.5f) * cellHeight
        return Offset(x, y)
    }
}
