package com.meet.compose.loaders.navigation

import kotlinx.serialization.Serializable

/**
 * Type-safe serializable navigation routes.
 */
@Serializable
sealed class ScreenRoute {

    @Serializable
    data object Gallery : ScreenRoute()

    @Serializable
    data class StudioDetail(val presetId: String) : ScreenRoute()
}
