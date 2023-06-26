package pl.adrianczerwinski.testdazn.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pl.adrianczerwinski.events.Events
import pl.adrianczerwinski.events.Schedule

@Composable
internal fun TestDaznNav(navHostController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = Destinations.BottomNav.EVENTS
    ) {
        composable(Destinations.BottomNav.EVENTS) {
            Events()
        }
        composable(Destinations.BottomNav.SCHEDULE) {
            Schedule()
        }
        composable(Destinations.BottomNav.VIDEO_PLAYER) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Video player")
            }
        }
    }
}

object Destinations {
    object BottomNav {
        const val EVENTS = "events"
        const val SCHEDULE = "schedule"
        const val VIDEO_PLAYER = "video_player"
    }
}
