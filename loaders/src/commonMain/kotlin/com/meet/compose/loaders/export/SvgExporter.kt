package com.meet.compose.loaders.export

import com.meet.compose.loaders.model.PixelAnimation

/**
 * Public exporter utility to convert pixel animation frame grids into SVG format.
 */
object SvgExporter {

    /**
     * Export a specific single frame of a [PixelAnimation] to SVG XML string.
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

    /**
     * Export the full multi-frame sequence of a [PixelAnimation] as an Animated SVG with CSS keyframes.
     */
    fun exportAnimatedSvg(
        animation: PixelAnimation,
        activeHex: String = "#6366F1",
        inactiveHex: String = "#E4E4E7",
        canvasSize: Int = 200,
        speedMultiplier: Float = 1.0f
    ): String {
        val columns = animation.columns
        val rows = animation.rows
        val totalFrames = animation.totalFrames
        if (totalFrames == 0) return ""

        val safeSpeed = maxOf(0.1f, speedMultiplier)
        val totalDurationSeconds = (totalFrames * animation.frameDurationMillis) / (1000f * safeSpeed)
        val cellSize = canvasSize.toFloat() / columns
        val radius = cellSize * 0.38f

        val sb = StringBuilder()
        sb.append("""<svg xmlns="http://www.w3.org/2000/svg" width="$canvasSize" height="$canvasSize" viewBox="0 0 $canvasSize $canvasSize">""")
        sb.append("\n  <style>\n")

        for (r in 0 until rows) {
            for (c in 0 until columns) {
                val states = (0 until totalFrames).map { f ->
                    animation.frames[f].grid.isPixelActive(c, r)
                }
                val allSame = states.all { it == states[0] }
                if (!allSame) {
                    val animName = "k_${c}_${r}"
                    sb.append("    @keyframes $animName {\n")
                    for (f in 0 until totalFrames) {
                        val percent = (f * 100.0 / totalFrames)
                        val percentStr = if (percent == 0.0) "0%" else "${percent.toInt()}%"
                        val fill = if (states[f]) activeHex else inactiveHex
                        sb.append("      $percentStr { fill: $fill; }\n")
                    }
                    val initialFill = if (states[0]) activeHex else inactiveHex
                    sb.append("      100% { fill: $initialFill; }\n")
                    sb.append("    }\n")
                    sb.append("    #dot_${c}_${r} { animation: $animName ${totalDurationSeconds}s steps(1) infinite; }\n")
                }
            }
        }

        sb.append("  </style>\n")

        val firstGrid = animation.frames[0].grid
        for (r in 0 until rows) {
            for (c in 0 until columns) {
                val isActive = firstGrid.isPixelActive(c, r)
                val fill = if (isActive) activeHex else inactiveHex
                val cx = (c + 0.5f) * cellSize
                val cy = (r + 0.5f) * cellSize
                sb.append("""  <circle id="dot_${c}_${r}" cx="$cx" cy="$cy" r="$radius" fill="$fill" />""").append("\n")
            }
        }

        sb.append("</svg>")
        return sb.toString()
    }
}
