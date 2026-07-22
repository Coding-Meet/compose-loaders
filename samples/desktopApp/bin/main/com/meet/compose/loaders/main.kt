package com.meet.compose.loaders

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "compose-loaders",
    ) {
        App()
    }
}