package com.meet.compose.loaders.presets.internal.frames

import com.meet.compose.loaders.model.PixelAnimation

internal object SandglassFrames {

    val grid5x5: PixelAnimation by lazy {
        PixelAnimation.fromGrids(
            columns = 5, rows = 5,
            frameGrids = listOf(
                // Full top
                listOf(
                    true,  true,  true,  true,  true,
                    false, true,  true,  true,  false,
                    false, false, false, false, false,
                    false, false, false, false, false,
                    false, false, false, false, false
                ),
                // Sand falling
                listOf(
                    true,  false, true,  false, true,
                    false, true,  true,  true,  false,
                    false, false, true,  false, false,
                    false, false, false, false, false,
                    false, false, true,  false, false
                ),
                // Half/half
                listOf(
                    true,  false, false, false, true,
                    false, true,  false, true,  false,
                    false, false, true,  false, false,
                    false, true,  true,  true,  false,
                    false, false, true,  false, false
                ),
                // Full bottom
                listOf(
                    false, false, false, false, false,
                    false, false, false, false, false,
                    false, false, false, false, false,
                    false, true,  true,  true,  false,
                    true,  true,  true,  true,  true
                )
            ),
            frameDurationMillis = 200L
        )
    }

    val grid7x7: PixelAnimation by lazy {
        PixelAnimation.fromGrids(
            columns = 7, rows = 7,
            frameGrids = listOf(
                // Full top
                listOf(
                    true,  true,  true,  true,  true,  true,  true,
                    false, true,  true,  true,  true,  true,  false,
                    false, false, true,  true,  true,  false, false,
                    false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false
                ),
                // Half way
                listOf(
                    true,  true,  false, false, false,  true,  true,
                    false, true,  true,  false, true,  true,  false,
                    false, false, true,  true,  true,  false, false,
                    false, false, false, true,  false, false, false,
                    false, false, false, false, false, false, false,
                    false, false, false, true,  false, false, false,
                    false, false, true,  true,  true,  false, false
                ),
                // Done
                listOf(
                    false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false,
                    false, false, true,  true,  true,  false, false,
                    false, true,  true,  true,  true,  true,  false,
                    true,  true,  true,  true,  true,  true,  true
                )
            ),
            frameDurationMillis = 200L
        )
    }
}
