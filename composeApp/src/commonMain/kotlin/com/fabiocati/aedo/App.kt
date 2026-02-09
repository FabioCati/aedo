package com.fabiocati.aedo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
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
import com.fabiocati.aedo.screens.home.HomeRoute
import com.fabiocati.aedo.screens.movieDetails.MovieDetailsRoute
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.getKoin

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
            entryProvider = entryProvider {
                entry<Destination.Home> {
                    HomeRoute(
                        onMovieClicked = { movieId ->
                            backStack.add(Destination.MovieDetail(movieId))
                        }
                    )
                }

                entry<Destination.MovieDetail> {
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
            }
        )
    }
}