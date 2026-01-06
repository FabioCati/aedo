package com.fabiocati.aedo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.fabiocati.aedo.navigation.Destination
import com.fabiocati.aedo.screens.home.HomeRoute
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    AedoTheme {
        val backStack = rememberNavBackStack(
            SavedStateConfiguration {
                serializersModule = SerializersModule {
                    polymorphic(NavKey::class) {
                        subclass(Destination.Home::class, Destination.Home.serializer())
                        subclass(Destination.MovieDetail::class, Destination.MovieDetail.serializer())
                        subclass(Destination.SeriesDetail::class, Destination.SeriesDetail.serializer())
                    }
                }
            },
            Destination.Home
        )

        NavDisplay(
            backStack = backStack,
            modifier = Modifier.fillMaxSize(),
            entryProvider = entryProvider {
                entry<Destination.Home> {
                    HomeRoute(
                        onNextPressed = {
                            backStack.add(Destination.MovieDetail("1"))
                        }
                    )
                }

                entry<Destination.MovieDetail> {
                    Text("ciao")
                }

                entry<Destination.SeriesDetail> {
                    Text("ciao")
                }
            }
        )
    }
}