package com.meet.compose.loaders.presets.internal.frames

import com.meet.compose.loaders.model.PixelAnimation

internal object EclipseFrames {

    val grid5x5: PixelAnimation by lazy {
        PixelAnimation.fromGrids(
            columns = 5, rows = 5,
            frameGrids = listOf(
                // Moon on left
                listOf(
                    false, true,  true,  true,  false,
                    true,  false, false, false, true,
                    true,  false, false, false, true,
                    true,  false, false, false, true,
                    false, true,  true,  true,  false
                ),
                // Cover
                listOf(
                    false, false, false, false, false,
                    false, false, false, false, false,
                    false, false, false, false, false,
                    false, false, false, false, false,
                    false, false, false, false, false
                ),
                // Moon on right
                listOf(
                    false, true,  true,  true,  false,
                    true,  false, false, false, true,
                    true,  false, false, false, true,
                    true,  false, false, false, true,
                    false, true,  true,  true,  false
                )
            ),
            frameDurationMillis = 200L
        )
    }

    val grid7x7: PixelAnimation by lazy {
        PixelAnimation.fromGrids(
            columns = 7, rows = 7,
            frameGrids = listOf(
                listOf(
                    false, false, true,  true,  true,  false, false,
                    false, true,  false, false, false, true,  false,
                    true,  false, false, false, false, false, true,
                    true,  false, false, false, false, false, true,
                    true,  false, false, false, false, false, true,
                    false, true,  false, false, false, true,  false,
                    false, false, true,  true,  true,  false, false
                ),
                listOf(
                    false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false
                ),
                listOf(
                    false, false, true,  true,  true,  false, false,
                    false, true,  false, false, false, true,  false,
                    true,  false, false, false, false, false, true,
                    true,  false, false, false, false, false, true,
                    true,  false, false, false, false, false, true,
                    false, true,  false, false, false, true,  false,
                    false, false, true,  true,  true,  false, false
                )
            ),
            frameDurationMillis = 200L
        )
    }
}
