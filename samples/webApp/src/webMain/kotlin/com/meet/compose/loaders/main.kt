package com.meet.compose.loaders

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
private fun ComposeViewport(content: @Composable () -> Unit) {
    val loader = document.querySelector(".loader-wrapper") as? org.w3c.dom.HTMLElement
    loader?.style?.display = "none"
    ComposeViewport(document.body!!, content)
}

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        App()
    }
}