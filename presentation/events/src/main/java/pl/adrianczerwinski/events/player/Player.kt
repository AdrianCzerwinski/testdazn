package pl.adrianczerwinski.events.player

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import pl.adrianczerwinski.common.BackPressHandler
import pl.adrianczerwinski.common.OnLifecycleEvent
import pl.adrianczerwinski.common.findActivity

@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun PlayerScreen(
    navController: NavController,
    url: String,
    viewModel: PlayerViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    val exoPlayer = ExoPlayer.Builder(context).build()
    var isFullScreen by rememberSaveable {
        mutableStateOf(false)
    }
    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }

    OnLifecycleEvent { _, event ->
        lifecycle = event
    }

    LaunchedEffect(viewModel) {
        systemUiController.isStatusBarVisible = false
        systemUiController.isNavigationBarVisible = false
    }

    BackPressHandler {
        context.findActivity().requestedOrientation = SCREEN_ORIENTATION_USER_PORTRAIT
        systemUiController.isStatusBarVisible = true
        systemUiController.isNavigationBarVisible = true
        if (!isFullScreen) navController.navigateUp() else isFullScreen = false
    }

    exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse(url)))
    exoPlayer.seekTo(uiState.position)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Player(exoPlayer = exoPlayer, lifecycle = lifecycle, onFullScreen = { isFullScreen = it }) {
            viewModel.savePlayerPosition(exoPlayer.contentPosition)
        }
    }
}

@SuppressLint("SourceLockedOrientationActivity")
@Composable
private fun Player(
    exoPlayer: ExoPlayer,
    lifecycle: Lifecycle.Event,
    onFullScreen: (Boolean) -> Unit,
    onDispose: () -> Unit
) {
    DisposableEffect(
        AndroidView(
            factory = {
                StyledPlayerView(it).apply {
                    this.player = exoPlayer
                    this.setFullscreenButtonClickListener { fullScreen ->
                        if (fullScreen) {
                            onFullScreen(true)
                            it.findActivity().requestedOrientation = SCREEN_ORIENTATION_USER_LANDSCAPE
                        } else {
                            onFullScreen(false)
                            it.findActivity().requestedOrientation = SCREEN_ORIENTATION_USER_PORTRAIT
                        }
                    }
                }
            },
            update = {
                when (lifecycle) {
                    Lifecycle.Event.ON_STOP -> {
                        it.onPause()
                        it.player?.stop()
                    }
                    Lifecycle.Event.ON_RESUME -> {
                        it.onResume()
                        it.player?.prepare()
                        it.player?.play()
                    }
                    else -> Unit
                }
            }
        )
    ) {
        exoPlayer.prepare()

        onDispose {
            onDispose()
            exoPlayer.release()
        }
    }
}
