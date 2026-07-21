package com.meet.compose.loaders.presets.internal

import com.meet.compose.loaders.model.PixelPreset
import com.meet.compose.loaders.presets.internal.frames.*

internal object GamingPresets {

    val TheClaw = PixelPreset(
        id = "theclaw",
        name = "TheClaw",
        grid5x5 = TheClawFrames.grid5x5,
        grid7x7 = TheClawFrames.grid7x7
    )

    val Galaxy = PixelPreset(
        id = "galaxy",
        name = "Galaxy",
        grid5x5 = GalaxyFrames.grid5x5,
        grid7x7 = GalaxyFrames.grid7x7
    )

    val Sleepy = PixelPreset(
        id = "sleepy",
        name = "Sleepy",
        grid5x5 = SleepyFrames.grid5x5,
        grid7x7 = SleepyFrames.grid7x7
    )

    val Pacman = PixelPreset(
        id = "pacman",
        name = "Pacman",
        grid5x5 = PacmanFrames.grid5x5,
        grid7x7 = PacmanFrames.grid7x7
    )

    val list: List<PixelPreset> = listOf(
        TheClaw, Galaxy, Sleepy, Pacman
    )
}