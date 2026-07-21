package com.meet.compose.loaders.model

import androidx.compose.runtime.Immutable

/**
 * Complete specification for a handcrafted pixel loading animation.
 *
 * @property columns Horizontal dimension of the dot matrix grid.
 * @property rows Vertical dimension of the dot matrix grid.
 * @property frames Sequence of animation frames.
 * @property frameDurationMillis Default playback duration per frame in milliseconds.
 */
@Immutable
data class PixelAnimation(
    val columns: Int,
    val rows: Int,
    val frames: List<Frame>,
    val frameDurationMillis: Long = 150L
) {
    val totalFrames: Int get() = frames.size

    val totalDurationMillis: Long get() = frames.sumOf { it.durationMillis ?: frameDurationMillis }

    companion object {
        /**
         * Factory function to create a [PixelAnimation] directly from boolean grid frame lists.
         */
        fun fromGrids(
            columns: Int,
            rows: Int,
            frameGrids: List<List<Boolean>>,
            frameDurationMillis: Long = 150L
        ): PixelAnimation {
            return PixelAnimation(
                columns = columns,
                rows = rows,
                frames = frameGrids.map { gridList ->
                    Frame(PixelGrid(columns, rows, gridList.toBooleanArray()))
                },
                frameDurationMillis = frameDurationMillis
            )
        }

        /**
         * Factory function to create a [PixelAnimation] from compact binary strings ("01001...").
         */
        fun fromBinaryStrings(
            columns: Int,
            rows: Int,
            binaryStrings: List<String>,
            frameDurationMillis: Long = 150L
        ): PixelAnimation {
            return PixelAnimation(
                columns = columns,
                rows = rows,
                frames = binaryStrings.map { str ->
                    val booleanArray = BooleanArray(str.length) { idx -> str[idx] == '1' }
                    Frame(PixelGrid(columns, rows, booleanArray))
                },
                frameDurationMillis = frameDurationMillis
            )
        }
    }
}
