package com.meet.compose.loaders.presets.internal.frames

import com.meet.compose.loaders.model.PixelAnimation

internal object InfinityWaveFrames {

    val grid5x5: PixelAnimation by lazy {
        PixelAnimation.fromGrids(
            columns = 5, rows = 5,
            frameGrids = listOf(
                listOf(
                    false, true,  false, true,  false,
                    true,  false, true,  false, true,
                    false, false, false, false, false,
                    false, false, false, false, false,
                    false, false, false, false, false
                ),
                listOf(
                    false, false, false, false, false,
                    false, true,  false, true,  false,
                    true,  false, true,  false, true,
                    false, false, false, false, false,
                    false, false, false, false, false
                ),
                listOf(
                    false, false, false, false, false,
                    false, false, false, false, false,
                    false, true,  false, true,  false,
                    true,  false, true,  false, true,
                    false, false, false, false, false
                )
            ),
            frameDurationMillis = 180L
        )
    }

    val grid7x7: PixelAnimation by lazy {
        PixelAnimation.fromGrids(
            columns = 7, rows = 7,
            frameGrids = listOf(
                listOf(
                    false, true,  true,  false, true,  true,  false,
                    true,  false, false, true,  false, false, true,
                    false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false
                ),
                listOf(
                    false, false, false, false, false, false, false,
                    false, true,  true,  false, true,  true,  false,
                    true,  false, false, true,  false, false, true,
                    false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false
                ),
                listOf(
                    false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false,
                    false, true,  true,  false, true,  true,  false,
                    true,  false, false, true,  false, false, true,
                    false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false
                )
            ),
            frameDurationMillis = 180L
        )
    }
}
