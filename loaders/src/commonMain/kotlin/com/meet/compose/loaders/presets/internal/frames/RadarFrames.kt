package com.meet.compose.loaders.presets.internal.frames

import com.meet.compose.loaders.model.PixelAnimation

internal object RadarFrames {

    val grid5x5: PixelAnimation by lazy {
        PixelAnimation.fromGrids(
            columns = 5, rows = 5,
            frameGrids = listOf(
                // Vertical
                listOf(
                    false, false, true,  false, false,
                    false, false, true,  false, false,
                    false, false, true,  false, false,
                    false, false, true,  false, false,
                    false, false, true,  false, false
                ),
                // Diagonal /
                listOf(
                    false, false, false, false, true,
                    false, false, false, true,  false,
                    false, false, true,  false, false,
                    false, true,  false, false, false,
                    true,  false, false, false, false
                ),
                // Horizontal
                listOf(
                    false, false, false, false, false,
                    false, false, false, false, false,
                    true,  true,  true,  true,  true,
                    false, false, false, false, false,
                    false, false, false, false, false
                ),
                // Diagonal \
                listOf(
                    true,  false, false, false, false,
                    false, true,  false, false, false,
                    false, false, true,  false, false,
                    false, false, false, true,  false,
                    false, false, false, false, true
                )
            ),
            frameDurationMillis = 150L
        )
    }

    val grid7x7: PixelAnimation by lazy {
        PixelAnimation.fromGrids(
            columns = 7, rows = 7,
            frameGrids = listOf(
                // Vertical
                listOf(
                    false, false, false, true,  false, false, false,
                    false, false, false, true,  false, false, false,
                    false, false, false, true,  false, false, false,
                    false, false, false, true,  false, false, false,
                    false, false, false, true,  false, false, false,
                    false, false, false, true,  false, false, false,
                    false, false, false, true,  false, false, false
                ),
                // Diagonal /
                listOf(
                    false, false, false, false, false, false, true,
                    false, false, false, false, false, true,  false,
                    false, false, false, false, true,  false, false,
                    false, false, false, true,  false, false, false,
                    false, false, true,  false, false, false, false,
                    false, true,  false, false, false, false, false,
                    true,  false, false, false, false, false, false
                ),
                // Horizontal
                listOf(
                    false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false,
                    true,  true,  true,  true,  true,  true,  true,
                    false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false
                ),
                // Diagonal \
                listOf(
                    true,  false, false, false, false, false, false,
                    false, true,  false, false, false, false, false,
                    false, false, true,  false, false, false, false,
                    false, false, false, true,  false, false, false,
                    false, false, false, false, true,  false, false,
                    false, false, false, false, false, true,  false,
                    false, false, false, false, false, false, true
                )
            ),
            frameDurationMillis = 150L
        )
    }
}
