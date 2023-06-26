package pl.adrianczerwinski.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode

@Composable
fun BoxScope.GradientOverlay(imageSize: Float) {
    val gradient = Brush.verticalGradient(
        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
        startY = imageSize / 2,
        endY = imageSize,
        tileMode = TileMode.Clamp
    )

    Box(modifier = Modifier.matchParentSize().background(brush = gradient))
}
