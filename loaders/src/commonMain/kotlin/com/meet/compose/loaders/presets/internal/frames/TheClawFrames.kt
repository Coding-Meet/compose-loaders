package com.meet.compose.loaders.presets.internal.frames

import com.meet.compose.loaders.model.PixelAnimation

internal object TheClawFrames {

    val grid5x5: PixelAnimation by lazy {
        PixelAnimation.fromGrids(
            columns = 5,
            rows = 5,
            frameGrids = listOf(
                listOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false),
            listOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false),
            listOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, true, false, false, false, false),
            listOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, false, false, true, true, false, false, false),
            listOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, false, false, true, true, true, false, false),
            listOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, false, false, true, true, true, false),
            listOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, false, false, true, true, true, false),
            listOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, false, false, true, true, true, false),
            listOf(false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, false, false, true, true, true, false),
            listOf(false, true, true, true, false, false, true, false, true, false, false, false, false, false, false, false, true, true, true, false, false, true, true, true, false),
            listOf(false, true, true, true, false, false, true, false, true, false, false, false, false, false, false, false, true, true, true, false, false, true, true, true, false),
            listOf(true, true, true, true, true, true, false, false, false, true, false, false, false, false, false, false, true, true, true, false, false, true, true, true, false),
            listOf(true, true, true, true, true, true, false, false, false, true, false, false, false, false, false, false, true, true, true, false, false, true, true, true, false),
            listOf(false, false, true, false, false, true, true, true, true, true, true, false, false, false, true, false, true, true, true, false, false, true, true, true, false),
            listOf(false, false, true, false, false, false, false, true, false, false, true, true, true, true, true, true, true, true, true, true, false, true, true, true, false),
            listOf(false, false, true, false, false, true, true, true, true, true, true, true, true, true, true, false, true, true, true, false, false, false, false, false, false),
            listOf(true, true, true, true, true, true, true, true, true, true, false, true, true, true, false, false, false, false, false, false, false, false, false, false, false),
            listOf(true, true, true, true, true, false, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false),
            listOf(false, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false),
            listOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false),
            listOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false)
            ),
            frameDurationMillis = 150L
        )
    }

    val grid7x7: PixelAnimation by lazy {
        PixelAnimation.fromGrids(
            columns = 7,
            rows = 7,
            frameGrids = listOf(
                listOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false),
            listOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false),
            listOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false),
            listOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, false, false, false, false, true, true, true, false, false, false, false, false, false, false, false, false, false, false),
            listOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, false, false, false, false, true, true, true, false, false, false, false, false, false, false, false, false, false),
            listOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, false, false, false, false, true, true, true, false, false, false, false, false, false, false, false, false),
            listOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, false, false, false, false, true, true, true, false, false, false, false, false, false, false, false, false),
            listOf(false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, false, false, false, false, true, true, true, false, false, false, false, false, false, false, false, false),
            listOf(false, false, true, true, true, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, false, false, false, false, true, true, true, false, false, false, false, false, false, false, false, false),
            listOf(false, false, false, true, false, false, false, false, false, true, true, true, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, true, true, true, false, false, false, false, true, true, true, false, false, false, false, false, false, false, false, false),
            listOf(false, false, false, true, false, false, false, false, false, true, true, true, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, true, true, true, false, false, false, false, true, true, true, false, false, false, false, false, false, false, false, false),
            listOf(false, false, false, true, false, false, false, false, true, true, true, true, true, false, false, true, false, false, false, true, false, false, false, false, false, false, false, false, false, false, true, true, true, false, false, false, false, true, true, true, false, false, false, false, false, false, false, false, false),
            listOf(false, false, false, true, false, false, false, false, true, true, true, true, true, false, false, true, false, false, false, true, false, false, false, false, false, false, false, false, false, false, true, true, true, false, false, false, false, true, true, true, false, false, false, false, false, false, false, false, false),
            listOf(false, false, false, true, false, false, false, false, false, false, true, false, false, false, false, true, true, true, true, true, false, false, true, false, false, false, true, false, false, false, true, true, true, false, false, false, false, true, true, true, false, false, false, false, false, false, false, false, false),
            listOf(false, false, false, true, false, false, false, false, false, false, true, false, false, false, false, false, false, true, false, false, false, false, true, true, true, true, true, false, false, true, true, true, true, true, false, false, false, true, true, true, false, false, false, false, false, false, false, false, false),
            listOf(false, false, false, true, false, false, false, false, false, false, true, false, false, false, false, true, true, true, true, true, false, false, true, true, true, true, true, false, false, false, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false),
            listOf(false, false, false, true, false, false, false, false, true, true, true, true, true, false, false, true, true, true, true, true, false, false, false, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false),
            listOf(false, true, true, true, true, true, false, false, true, true, true, true, true, false, false, false, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false),
            listOf(false, true, true, true, true, true, false, false, false, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false),
            listOf(false, false, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false),
            listOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false)
            ),
            frameDurationMillis = 150L
        )
    }
}