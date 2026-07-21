package com.meet.compose.loaders.export

import com.meet.compose.loaders.model.PixelAnimation

/**
 * Public exporter utility to convert pixel animation frame grids into SVG format.
 */
object SvgExporter {

    /**
     * Export a specific frame of a [PixelAnimation] to SVG XML string.
     */
    fun exportFrameToSvg(
        animation: PixelAnimation,
        frameIndex: Int = 0,
        activeHex: String = "#6366F1",
        inactiveHex: String = "#E4E4E7",
        canvasSize: Int = 200
    ): String {
        val columns = animation.columns
        val rows = animation.rows
        val frame = animation.frames.getOrNull(frameIndex) ?: return ""
        val grid = frame.grid

        val cellSize = canvasSize.toFloat() / columns
        val radius = cellSize * 0.38f

        val sb = StringBuilder()
        sb.append("""<svg xmlns="http://www.w3.org/2000/svg" width="$canvasSize" height="$canvasSize" viewBox="0 0 $canvasSize $canvasSize">""")
        sb.append("\n")

        for (r in 0 until rows) {
            for (c in 0 until columns) {
                val isActive = grid.isPixelActive(c, r)
                val fill = if (isActive) activeHex else inactiveHex
                val cx = (c + 0.5f) * cellSize
                val cy = (r + 0.5f) * cellSize

                sb.append("""  <circle cx="$cx" cy="$cy" r="$radius" fill="$fill" />""").append("\n")
            }
        }

        sb.append("</svg>")
        return sb.toString()
    }
}
