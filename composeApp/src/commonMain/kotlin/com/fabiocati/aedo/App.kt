package com.fabiocati.aedo

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.fabiocati.aedo.navigation.Destination
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import com.fabiocati.aedo.navigation.scenes.AedoListDetailSceneStrategy
import com.fabiocati.aedo.navigation.scenes.rememberAedoListDetailSceneStrategy
import com.fabiocati.aedo.screens.home.HomeRoute
import com.fabiocati.aedo.screens.movieDetails.MovieDetailsRoute
import com.fabiocati.aedo.screens.settings.SettingsRoute
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.compose.getKoin

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun App() {
    AedoTheme {
        val koin = getKoin()
        val backStack = rememberNavBackStack(
            SavedStateConfiguration {
                serializersModule = SerializersModule {
                    polymorphic(NavKey::class) {
                        subclass(Destination.Home::class, Destination.Home.serializer())
                        subclass(Destination.MovieDetail::class, Destination.MovieDetail.serializer())
                        subclass(Destination.Settings::class, Destination.Settings.serializer())
                    }
                }
            },
            Destination.Home
        )

        NavDisplay(
            backStack = backStack,
            modifier = Modifier.fillMaxSize(),
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            sceneStrategy = rememberListDetailSceneStrategy(),
            entryProvider = entryProvider {
                entry<Destination.Home>(
                    metadata = ListDetailSceneStrategy.listPane()
                ) {
                    HomeRoute(
                        onMovieClicked = { movieId ->
                            backStack.add(Destination.MovieDetail(movieId))
                        },
                        onSettingsClicked = {
                            backStack.add(Destination.Settings)
                        }
                    )
                }

                entry<Destination.MovieDetail>(
                    metadata = ListDetailSceneStrategy.detailPane()
                ) {
                    MovieDetailsRoute(
                        movieId = it.id,
                        onTrailerClick = { trailer ->
                            koin.get<UrlOpener>().openUrl(trailer.url)
                        },
                        onMovieClick = { movieId ->
                            backStack.add(Destination.MovieDetail(movieId))
                        },
                        onBackClick = {
                            backStack.removeLastOrNull()
                        }
                    )
                }

                entry<Destination.Settings>(
                    metadata = ListDetailSceneStrategy.detailPane()
                ) {
                    SettingsRoute(
                        onBackClick = {
                            backStack.removeLastOrNull()
                        }
                    )
                }
            },
            transitionSpec = {
                // Slide in from right when navigating forward
                slideInHorizontally(initialOffsetX = { it }) togetherWith slideOutHorizontally(targetOffsetX = { -it })
            },
            popTransitionSpec = {
                slideInHorizontally(initialOffsetX = { -it }) togetherWith slideOutHorizontally(targetOffsetX = { it })
            },
            predictivePopTransitionSpec = {
                slideInHorizontally(initialOffsetX = { -it }) togetherWith slideOutHorizontally(targetOffsetX = { it })
            },
        )
    }
}