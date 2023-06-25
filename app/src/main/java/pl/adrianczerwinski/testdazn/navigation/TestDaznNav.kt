package pl.adrianczerwinski.testdazn.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun TestDaznNav(navHostController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = Destinations.BottomNav.EVENTS
    ) {
        composable(Destinations.BottomNav.EVENTS) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Events")
            }
        }
        composable(Destinations.BottomNav.SCHEDULE) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Schedule")
            }
        }
        composable(Destinations.BottomNav.VIDEO_PLAYER) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Schedule")
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
