package com.meet.compose.loaders.presets.internal.frames

import com.meet.compose.loaders.model.PixelAnimation

internal object HelixFrames {

    val grid5x5: PixelAnimation by lazy {
        PixelAnimation.fromGrids(
            columns = 5, rows = 5,
            frameGrids = listOf(
                listOf(
                    true,  false, false, false, true,
                    true,  false, false, false, true,
                    true,  false, false, false, true,
                    true,  false, false, false, true,
                    true,  false, false, false, true
                ),
                listOf(
                    false, true,  false, true,  false,
                    false, true,  false, true,  false,
                    false, true,  false, true,  false,
                    false, true,  false, true,  false,
                    false, true,  false, true,  false
                ),
                listOf(
                    false, false, true,  false, false,
                    false, false, true,  false, false,
                    false, false, true,  false, false,
                    false, false, true,  false, false,
                    false, false, true,  false, false
                ),
                listOf(
                    false, true,  false, true,  false,
                    false, true,  false, true,  false,
                    false, true,  false, true,  false,
                    false, true,  false, true,  false,
                    false, true,  false, true,  false
                )
            ),
            frameDurationMillis = 150L
        )
    }

    val grid7x7: PixelAnimation by lazy {
        PixelAnimation.fromGrids(
            columns = 7, rows = 7,
            frameGrids = listOf(
                listOf(
                    true,  false, false, false, false, false, true,
                    true,  false, false, false, false, false, true,
                    true,  false, false, false, false, false, true,
                    true,  false, false, false, false, false, true,
                    true,  false, false, false, false, false, true,
                    true,  false, false, false, false, false, true,
                    true,  false, false, false, false, false, true
                ),
                listOf(
                    false, true,  false, false, false, true,  false,
                    false, true,  false, false, false, true,  false,
                    false, true,  false, false, false, true,  false,
                    false, true,  false, false, false, true,  false,
                    false, true,  false, false, false, true,  false,
                    false, true,  false, false, false, true,  false,
                    false, true,  false, false, false, true,  false
                ),
                listOf(
                    false, false, true,  false, true,  false, false,
                    false, false, true,  false, true,  false, false,
                    false, false, true,  false, true,  false, false,
                    false, false, true,  false, true,  false, false,
                    false, false, true,  false, true,  false, false,
                    false, false, true,  false, true,  false, false,
                    false, false, true,  false, true,  false, false
                ),
                listOf(
                    false, false, false, true,  false, false, false,
                    false, false, false, true,  false, false, false,
                    false, false, false, true,  false, false, false,
                    false, false, false, true,  false, false, false,
                    false, false, false, true,  false, false, false,
                    false, false, false, true,  false, false, false,
                    false, false, false, true,  false, false, false
                )
            ),
            frameDurationMillis = 150L
        )
    }
}
