package com.fabiocati.aedo.navigation.scenes

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.scene.Scene
import androidx.navigation3.scene.SceneStrategy
import androidx.navigation3.scene.SceneStrategyScope

class NoSenseScene<T : Any>(
    override val key: Any,
    override val previousEntries: List<NavEntry<T>>,
    val listEntry: NavEntry<T>,
    val detailEntries: List<NavEntry<T>>,
) : Scene<T> {
    override val entries: List<NavEntry<T>> = listOf(listEntry) + detailEntries

    override val content: @Composable (() -> Unit) = {

        Box(Modifier.fillMaxSize()) {
            entries.first().Content()

            LazyVerticalGrid(
                modifier = Modifier.animateContentSize(),
                columns = GridCells.Fixed(2)
            ) {
                items(entries.drop(1)) { entry ->
                    Box(modifier = Modifier.width(200.dp).aspectRatio(9 / 16f)) {
                        entry.Content()
                    }

                }
            }
        }
    }
}

class NoSenseSceneStrategy<T : Any> : SceneStrategy<T> {
    override fun SceneStrategyScope<T>.calculateScene(entries: List<NavEntry<T>>): Scene<T> {
        val listEntry = entries.first()
        return NoSenseScene(
            key = listEntry.contentKey,
            previousEntries = entries.dropLast(1),
            listEntry = listEntry,
            detailEntries = entries.drop(1),
        )
    }

}

@Composable
fun <T : Any> rememberNoSceneStrategy(): NoSenseSceneStrategy<T> = remember { NoSenseSceneStrategy() }