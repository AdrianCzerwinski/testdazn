package pl.adrianczerwinski.testdazn.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pl.adrianczerwinski.events.Events
import pl.adrianczerwinski.events.Schedule
import pl.adrianczerwinski.events.player.PlayerScreen

@Composable
internal fun TestDaznNav(navHostController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = Destinations.BottomNav.EVENTS
    ) {
        composable(Destinations.BottomNav.EVENTS) {
            Events { url ->
                navHostController.currentBackStackEntry?.savedStateHandle?.set("url", url)
                navHostController.navigate(Destinations.VIDEO_PLAYER)
            }
        }
        composable(Destinations.BottomNav.SCHEDULE) {
            Schedule()
        }
        composable(route = Destinations.VIDEO_PLAYER) {
            val url = navHostController.previousBackStackEntry?.savedStateHandle?.get<String>("url")
            url?.let {
                PlayerScreen(
                    url = it,
                    navController = navHostController
                )
            }
        }
    }
}

object Destinations {

    const val VIDEO_PLAYER = "video_player"
    object BottomNav {
        const val EVENTS = "events"
        const val SCHEDULE = "schedule"
    }
}
