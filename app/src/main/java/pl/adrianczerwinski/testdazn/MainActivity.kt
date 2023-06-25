package pl.adrianczerwinski.testdazn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import pl.adrianczerwinski.testdazn.navigation.Destinations
import pl.adrianczerwinski.testdazn.navigation.TestDaznNav
import pl.adrianczerwinski.ui.theme.TestDaznTheme

class MainActivity : ComponentActivity() {

    private val navOptions = navOptions {
        popUpTo(Destinations.BottomNav.EVENTS) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = !isSystemInDarkTheme()
            TestDaznTheme(
                dynamicColor = true
            ) {
                val backgroundColor = MaterialTheme.colorScheme.background
                val navController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = backStackEntry?.destination

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = backgroundColor,
                        darkIcons = useDarkIcons
                    )
                }
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Scaffold(
                        bottomBar = {
                            if (currentDestination?.route == Destinations.BottomNav.EVENTS ||
                                currentDestination?.route == Destinations.BottomNav.SCHEDULE
                            ) {
                                BottomNavBar(
                                    currentRoute = currentDestination.route ?: Destinations.BottomNav.EVENTS,
                                    onEventsClick = { navController.navigate(Destinations.BottomNav.EVENTS, navOptions) },
                                    onScheduleClick = { navController.navigate(Destinations.BottomNav.SCHEDULE, navOptions) }
                                )
                            }
                        }
                    ) {
                        TestDaznNav(modifier = Modifier.padding(it), navHostController = navController)
                    }
                }
            }
        }
    }
}
