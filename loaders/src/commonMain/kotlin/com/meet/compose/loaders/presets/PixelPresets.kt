package com.meet.compose.loaders.presets

import com.meet.compose.loaders.model.PixelPreset
import com.meet.compose.loaders.presets.internal.BasicPresets
import com.meet.compose.loaders.presets.internal.GamingPresets
import com.meet.compose.loaders.presets.internal.MotionPresets
import com.meet.compose.loaders.presets.internal.TechPresets

/**
 * Public catalog containing built-in handcrafted pixel animation presets.
 */
object PixelPresets {
    val Framer: PixelPreset get() = BasicPresets.Framer
    val Gradient: PixelPreset get() = BasicPresets.Gradient
    val Target: PixelPreset get() = BasicPresets.Target
    val Unboxing: PixelPreset get() = BasicPresets.Unboxing
    val DotDotDot: PixelPreset get() = BasicPresets.DotDotDot
    val TinySpinner: PixelPreset get() = BasicPresets.TinySpinner
    val Loading: PixelPreset get() = BasicPresets.Loading
    val Importing: PixelPreset get() = BasicPresets.Importing
    val Searching: PixelPreset get() = BasicPresets.Searching
    val Busy: PixelPreset get() = BasicPresets.Busy
    val Saving: PixelPreset get() = BasicPresets.Saving
    val Initialising: PixelPreset get() = BasicPresets.Initialising
    val ClassicLoading: PixelPreset get() = BasicPresets.ClassicLoading
    val Newton: PixelPreset get() = MotionPresets.Newton
    val Bounce: PixelPreset get() = MotionPresets.Bounce
    val Syncing: PixelPreset get() = MotionPresets.Syncing
    val Ripple: PixelPreset get() = MotionPresets.Ripple
    val RollingDice: PixelPreset get() = MotionPresets.RollingDice
    val Tumbleweed: PixelPreset get() = MotionPresets.Tumbleweed
    val TheGreatWave: PixelPreset get() = MotionPresets.TheGreatWave
    val Rocket: PixelPreset get() = TechPresets.Rocket
    val Comet: PixelPreset get() = TechPresets.Comet
    val Infinite: PixelPreset get() = TechPresets.Infinite
    val BarChart: PixelPreset get() = TechPresets.BarChart
    val Flash: PixelPreset get() = TechPresets.Flash
    val LoadingGraph: PixelPreset get() = TechPresets.LoadingGraph
    val LoadingGIFs: PixelPreset get() = TechPresets.LoadingGIFs
    val TheClaw: PixelPreset get() = GamingPresets.TheClaw
    val Galaxy: PixelPreset get() = GamingPresets.Galaxy
    val Sleepy: PixelPreset get() = GamingPresets.Sleepy
    val Pacman: PixelPreset get() = GamingPresets.Pacman

    /**
     * Complete catalog list of all animation presets.
     */
    val all: List<PixelPreset> = (
        BasicPresets.list +
        MotionPresets.list +
        TechPresets.list +
        GamingPresets.list
    )
}