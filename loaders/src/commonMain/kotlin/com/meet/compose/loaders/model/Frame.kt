package com.meet.compose.loaders.model

import androidx.compose.runtime.Immutable

/**
 * Single animation frame holding pixel grid state and optional custom duration.
 *
 * @property grid The pixel grid state for this frame.
 * @property durationMillis Custom display duration for this frame in milliseconds, or null to use global frame rate.
 */
@Immutable
data class Frame(
    val grid: PixelGrid,
    val durationMillis: Long? = null
)
