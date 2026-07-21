package com.meet.compose.loaders.render.internal

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.meet.compose.loaders.engine.internal.GridMath
import com.meet.compose.loaders.model.PixelAnimation
import com.meet.compose.loaders.model.PixelShape

/**
 * Internal hardware-accelerated canvas renderer for pixel grids.
 */
internal object PixelCanvasRenderer {

    fun drawPixelFrame(
        drawScope: DrawScope,
        animation: PixelAnimation,
        frameIndex: Int,
        activeColor: Color,
        inactiveColor: Color,
        shape: PixelShape = PixelShape.Circle
    ) {
        val width = drawScope.size.width
        val height = drawScope.size.height
        val columns = animation.columns
        val rows = animation.rows

        val frame = animation.frames.getOrNull(frameIndex) ?: return
        val grid = frame.grid

        val radius = GridMath.calculateDotRadius(width, height, columns, rows)
        val diameter = radius * 2f

        for (r in 0 until rows) {
            for (c in 0 until columns) {
                val isActive = grid.isPixelActive(c, r)
                val dotColor = if (isActive) activeColor else inactiveColor
                val center = GridMath.calculateCenterOffset(c, r, width, height, columns, rows)

                when (shape) {
                    PixelShape.Circle -> {
                        drawScope.drawCircle(
                            color = dotColor,
                            radius = radius,
                            center = center
                        )
                    }
                    PixelShape.Square -> {
                        drawScope.drawRect(
                            color = dotColor,
                            topLeft = Offset(center.x - radius, center.y - radius),
                            size = Size(diameter, diameter)
                        )
                    }
                    PixelShape.RoundedSquare -> {
                        drawScope.drawRoundRect(
                            color = dotColor,
                            topLeft = Offset(center.x - radius, center.y - radius),
                            size = Size(diameter, diameter),
                            cornerRadius = CornerRadius(radius * 0.4f, radius * 0.4f)
                        )
                    }
                }
            }
        }
    }
}
