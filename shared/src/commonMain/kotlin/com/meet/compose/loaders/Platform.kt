package com.meet.compose.loaders

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform