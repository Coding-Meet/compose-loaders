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

    val PingPong = PixelPreset(
        id = "pingpong",
        name = "PingPong",
        grid5x5 = PingpongFrames.grid5x5,
        grid7x7 = PingpongFrames.grid7x7
    )

    val PacLoop = PixelPreset(
        id = "pacloop",
        name = "PacLoop",
        grid5x5 = PacLoopFrames.grid5x5,
        grid7x7 = PacLoopFrames.grid7x7
    )

    val Snake = PixelPreset(
        id = "snake",
        name = "Snake",
        grid5x5 = SnakeFrames.grid5x5,
        grid7x7 = SnakeFrames.grid7x7
    )

    val Tetris = PixelPreset(
        id = "tetris",
        name = "Tetris",
        grid5x5 = TetrisFrames.grid5x5,
        grid7x7 = TetrisFrames.grid7x7
    )

    val list: List<PixelPreset> = listOf(
        TheClaw, Galaxy, Sleepy, Pacman, PingPong, PacLoop, Snake, Tetris
    )
}