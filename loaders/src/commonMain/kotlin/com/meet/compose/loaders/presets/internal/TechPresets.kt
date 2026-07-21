package com.meet.compose.loaders.presets.internal

import com.meet.compose.loaders.model.PixelPreset
import com.meet.compose.loaders.presets.internal.frames.*

internal object TechPresets {

    val Rocket = PixelPreset(
        id = "rocket",
        name = "Rocket",
        grid5x5 = RocketFrames.grid5x5,
        grid7x7 = RocketFrames.grid7x7
    )

    val Comet = PixelPreset(
        id = "comet",
        name = "Comet",
        grid5x5 = CometFrames.grid5x5,
        grid7x7 = CometFrames.grid7x7
    )

    val Infinite = PixelPreset(
        id = "infinite",
        name = "Infinite",
        grid5x5 = InfiniteFrames.grid5x5,
        grid7x7 = InfiniteFrames.grid7x7
    )

    val BarChart = PixelPreset(
        id = "barchart",
        name = "BarChart",
        grid5x5 = BarChartFrames.grid5x5,
        grid7x7 = BarChartFrames.grid7x7
    )

    val Flash = PixelPreset(
        id = "flash",
        name = "Flash",
        grid5x5 = FlashFrames.grid5x5,
        grid7x7 = FlashFrames.grid7x7
    )

    val LoadingGraph = PixelPreset(
        id = "loadinggraph",
        name = "LoadingGraph",
        grid5x5 = LoadingGraphFrames.grid5x5,
        grid7x7 = LoadingGraphFrames.grid7x7
    )

    val LoadingGIFs = PixelPreset(
        id = "loadinggifs",
        name = "LoadingGIFs",
        grid5x5 = LoadingGIFsFrames.grid5x5,
        grid7x7 = LoadingGIFsFrames.grid7x7
    )

    val list: List<PixelPreset> = listOf(
        Rocket, Comet, Infinite, BarChart, Flash, LoadingGraph, LoadingGIFs
    )
}