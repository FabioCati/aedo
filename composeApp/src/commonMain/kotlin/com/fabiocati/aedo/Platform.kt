package com.fabiocati.aedo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform