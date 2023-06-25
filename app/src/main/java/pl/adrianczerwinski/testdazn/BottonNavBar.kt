package pl.adrianczerwinski.testdazn

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.adrianczerwinski.testdazn.navigation.Destinations

@Composable
internal fun BottomNavBar(
    currentRoute: String,
    onEventsClick: () -> Unit = {},
    onScheduleClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier,
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.background
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(BottomAppBarDefaults.ContentPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            EventsBottomNavItem(currentRoute = currentRoute) { onEventsClick() }
            ScheduleBottomNavItem(currentRoute = currentRoute) { onScheduleClick() }
        }
    }
}

@Composable
private fun RowScope.EventsBottomNavItem(currentRoute: String, onEventsClick: () -> Unit) = Column(
    modifier = Modifier.weight(1f),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
) {
    val contentColor = if (currentRoute == Destinations.BottomNav.EVENTS) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.tertiary
    }
    Icon(
        modifier = Modifier
            .clickable { onEventsClick() }
            .padding(12.dp)
            .size(32.dp),
        imageVector = Icons.Filled.Home,
        contentDescription = "Events Icon",
        tint = contentColor
    )
}

@Composable
private fun RowScope.ScheduleBottomNavItem(currentRoute: String, onScheduleClick: () -> Unit) = Column(
    modifier = Modifier.weight(1f),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
) {
    val contentColor = if (currentRoute == Destinations.BottomNav.EVENTS) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.tertiary
    }
    Icon(
        modifier = Modifier
            .clickable { onScheduleClick() }
            .padding(12.dp)
            .size(32.dp),
        imageVector = Icons.Filled.DateRange,
        contentDescription = "Schedule Icon",
        tint = contentColor
    )
}
