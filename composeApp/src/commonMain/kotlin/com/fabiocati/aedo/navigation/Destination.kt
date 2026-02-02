package com.fabiocati.aedo.navigation

import kotlinx.serialization.Serializable
import androidx.navigation3.runtime.NavKey

@Serializable
sealed interface Destination : NavKey {

    @Serializable
    data object Home : Destination

    @Serializable
    data class MovieDetail(val id: Int) : Destination
}