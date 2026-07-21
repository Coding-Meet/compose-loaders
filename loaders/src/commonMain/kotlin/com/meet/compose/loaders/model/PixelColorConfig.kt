package com.meet.compose.loaders.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Color configuration for rendering pixel grid states and canvas background.
 */
@Immutable
data class PixelColorConfig(
    val activeColor: Color,
    val inactiveColor: Color,
    val backgroundColor: Color = Color.Transparent
)
