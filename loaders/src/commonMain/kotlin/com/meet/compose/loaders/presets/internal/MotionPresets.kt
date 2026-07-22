package com.meet.compose.loaders.presets.internal

import com.meet.compose.loaders.model.PixelPreset
import com.meet.compose.loaders.presets.internal.frames.*

internal object MotionPresets {

    val Newton = PixelPreset(
        id = "newton",
        name = "Newton",
        grid5x5 = NewtonFrames.grid5x5,
        grid7x7 = NewtonFrames.grid7x7
    )

    val Bounce = PixelPreset(
        id = "bounce",
        name = "Bounce",
        grid5x5 = BounceFrames.grid5x5,
        grid7x7 = BounceFrames.grid7x7
    )

    val Syncing = PixelPreset(
        id = "syncing",
        name = "Syncing",
        grid5x5 = SyncingFrames.grid5x5,
        grid7x7 = SyncingFrames.grid7x7
    )

    val Ripple = PixelPreset(
        id = "ripple",
        name = "Ripple",
        grid5x5 = RippleFrames.grid5x5,
        grid7x7 = RippleFrames.grid7x7
    )

    val RollingDice = PixelPreset(
        id = "rollingdice",
        name = "RollingDice",
        grid5x5 = RollingDiceFrames.grid5x5,
        grid7x7 = RollingDiceFrames.grid7x7
    )

    val Tumbleweed = PixelPreset(
        id = "tumbleweed",
        name = "Tumbleweed",
        grid5x5 = TumbleweedFrames.grid5x5,
        grid7x7 = TumbleweedFrames.grid7x7
    )

    val TheGreatWave = PixelPreset(
        id = "thegreatwave",
        name = "TheGreatWave",
        grid5x5 = TheGreatWaveFrames.grid5x5,
        grid7x7 = TheGreatWaveFrames.grid7x7
    )

    val Heartbeat = PixelPreset(
        id = "heartbeat",
        name = "Heartbeat",
        grid5x5 = HeartbeatFrames.grid5x5,
        grid7x7 = HeartbeatFrames.grid7x7
    )

    val Radar = PixelPreset(
        id = "radar",
        name = "Radar",
        grid5x5 = RadarFrames.grid5x5,
        grid7x7 = RadarFrames.grid7x7
    )

    val Fire = PixelPreset(
        id = "fire",
        name = "Fire",
        grid5x5 = FireFrames.grid5x5,
        grid7x7 = FireFrames.grid7x7
    )

    val PulseGrid = PixelPreset(
        id = "pulsegrid",
        name = "PulseGrid",
        grid5x5 = PulseGridFrames.grid5x5,
        grid7x7 = PulseGridFrames.grid7x7
    )

    val Windmill = PixelPreset(
        id = "windmill",
        name = "Windmill",
        grid5x5 = WindmillFrames.grid5x5,
        grid7x7 = WindmillFrames.grid7x7
    )

    val Tornado = PixelPreset(
        id = "tornado",
        name = "Tornado",
        grid5x5 = TornadoFrames.grid5x5,
        grid7x7 = TornadoFrames.grid7x7
    )

    val Sandglass = PixelPreset(
        id = "sandglass",
        name = "Sandglass",
        grid5x5 = SandglassFrames.grid5x5,
        grid7x7 = SandglassFrames.grid7x7
    )

    val list: List<PixelPreset> = listOf(
        Newton, Bounce, Syncing, Ripple, RollingDice, Tumbleweed, TheGreatWave, Heartbeat, Radar, Fire, PulseGrid, Windmill, Tornado, Sandglass
    )
}