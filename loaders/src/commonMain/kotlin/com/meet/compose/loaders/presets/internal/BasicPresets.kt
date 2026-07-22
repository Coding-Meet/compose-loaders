package com.meet.compose.loaders.presets.internal

import com.meet.compose.loaders.model.PixelPreset
import com.meet.compose.loaders.presets.internal.frames.*

internal object BasicPresets {

    val Framer = PixelPreset(
        id = "framer",
        name = "Framer",
        grid5x5 = FramerFrames.grid5x5,
        grid7x7 = FramerFrames.grid7x7
    )

    val Gradient = PixelPreset(
        id = "gradient",
        name = "Gradient",
        grid5x5 = GradientFrames.grid5x5,
        grid7x7 = GradientFrames.grid7x7
    )

    val Target = PixelPreset(
        id = "target",
        name = "Target",
        grid5x5 = TargetFrames.grid5x5,
        grid7x7 = TargetFrames.grid7x7
    )

    val Unboxing = PixelPreset(
        id = "unboxing",
        name = "Unboxing",
        grid5x5 = UnboxingFrames.grid5x5,
        grid7x7 = UnboxingFrames.grid7x7
    )

    val DotDotDot = PixelPreset(
        id = "dotdotdot",
        name = "DotDotDot",
        grid5x5 = DotDotDotFrames.grid5x5,
        grid7x7 = DotDotDotFrames.grid7x7
    )

    val TinySpinner = PixelPreset(
        id = "tinyspinner",
        name = "TinySpinner",
        grid5x5 = TinySpinnerFrames.grid5x5,
        grid7x7 = TinySpinnerFrames.grid7x7
    )

    val Loading = PixelPreset(
        id = "loading",
        name = "Loading",
        grid5x5 = LoadingFrames.grid5x5,
        grid7x7 = LoadingFrames.grid7x7
    )

    val Importing = PixelPreset(
        id = "importing",
        name = "Importing",
        grid5x5 = ImportingFrames.grid5x5,
        grid7x7 = ImportingFrames.grid7x7
    )

    val Searching = PixelPreset(
        id = "searching",
        name = "Searching",
        grid5x5 = SearchingFrames.grid5x5,
        grid7x7 = SearchingFrames.grid7x7
    )

    val Busy = PixelPreset(
        id = "busy",
        name = "Busy",
        grid5x5 = BusyFrames.grid5x5,
        grid7x7 = BusyFrames.grid7x7
    )

    val Saving = PixelPreset(
        id = "saving",
        name = "Saving",
        grid5x5 = SavingFrames.grid5x5,
        grid7x7 = SavingFrames.grid7x7
    )

    val Initialising = PixelPreset(
        id = "initialising",
        name = "Initialising",
        grid5x5 = InitialisingFrames.grid5x5,
        grid7x7 = InitialisingFrames.grid7x7
    )

    val ClassicLoading = PixelPreset(
        id = "classicloading",
        name = "ClassicLoading",
        grid5x5 = ClassicLoadingFrames.grid5x5,
        grid7x7 = ClassicLoadingFrames.grid7x7
    )

    val Crosshair = PixelPreset(
        id = "crosshair",
        name = "Crosshair",
        grid5x5 = CrosshairFrames.grid5x5,
        grid7x7 = CrosshairFrames.grid7x7
    )

    val Scanner = PixelPreset(
        id = "scanner",
        name = "Scanner",
        grid5x5 = ScannerFrames.grid5x5,
        grid7x7 = ScannerFrames.grid7x7
    )

    val Eclipse = PixelPreset(
        id = "eclipse",
        name = "Eclipse",
        grid5x5 = EclipseFrames.grid5x5,
        grid7x7 = EclipseFrames.grid7x7
    )

    val list: List<PixelPreset> = listOf(
        Framer, Gradient, Target, Unboxing, DotDotDot, TinySpinner, Loading, Importing, Searching, Busy, Saving, Initialising, ClassicLoading, Crosshair, Scanner, Eclipse
    )
}