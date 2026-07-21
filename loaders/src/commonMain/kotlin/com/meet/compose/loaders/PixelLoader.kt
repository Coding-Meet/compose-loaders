package com.meet.compose.loaders

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.meet.compose.loaders.model.PixelAnimation
import com.meet.compose.loaders.model.PixelGridSize
import com.meet.compose.loaders.model.PixelPreset
import com.meet.compose.loaders.model.PixelShape
import com.meet.compose.loaders.presets.PixelPresets
import com.meet.compose.loaders.render.internal.PixelCanvasRenderer
import kotlinx.coroutines.delay

/**
 * High-level public Composable entrypoint for rendering pixel loading animations.
 */
@Composable
fun PixelLoader(
    preset: PixelPreset = PixelPresets.Framer,
    gridSize: PixelGridSize = PixelGridSize.Grid5x5,
    modifier: Modifier = Modifier.size(48.dp),
    color: Color = Color(0xFF6366F1),
    inactiveColor: Color = Color(0xFFE4E4E7),
    backgroundColor: Color = Color.Transparent,
    shape: PixelShape = PixelShape.Circle,
    speedMultiplier: Float = 1.0f
) {
    val animation = preset.selectAnimation(gridSize)

    PixelLoader(
        animation = animation,
        modifier = modifier,
        color = color,
        inactiveColor = inactiveColor,
        backgroundColor = backgroundColor,
        shape = shape,
        speedMultiplier = speedMultiplier
    )
}

/**
 * Overload allowing custom [PixelAnimation] frame sequences.
 */
@Composable
fun PixelLoader(
    animation: PixelAnimation,
    modifier: Modifier = Modifier.size(48.dp),
    color: Color = Color(0xFF6366F1),
    inactiveColor: Color = Color(0xFFE4E4E7),
    backgroundColor: Color = Color.Transparent,
    shape: PixelShape = PixelShape.Circle,
    speedMultiplier: Float = 1.0f
) {
    var frameIndex by remember(animation) { mutableIntStateOf(0) }

    val safeSpeed = maxOf(0.1f, speedMultiplier)
    val totalFrames = animation.totalFrames

    if (totalFrames > 0 && safeSpeed > 0.01f) {
        LaunchedEffect(animation, safeSpeed) {
            while (true) {
                val currentFrame = animation.frames.getOrNull(frameIndex)
                val duration = (currentFrame?.durationMillis ?: animation.frameDurationMillis)
                val adjustedDelay = (duration / safeSpeed).toLong()

                delay(maxOf(16L, adjustedDelay))
                frameIndex = (frameIndex + 1) % totalFrames
            }
        }
    }

    Box(
        modifier = modifier.background(backgroundColor)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            PixelCanvasRenderer.drawPixelFrame(
                drawScope = this,
                animation = animation,
                frameIndex = frameIndex,
                activeColor = color,
                inactiveColor = inactiveColor,
                shape = shape
            )
        }
    }
}
