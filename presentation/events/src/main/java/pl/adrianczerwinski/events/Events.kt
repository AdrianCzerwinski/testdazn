package pl.adrianczerwinski.events

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImagePainter.State.Success
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import pl.adrianczerwinski.events.EventsUiState.ScreenState.ERROR
import pl.adrianczerwinski.events.EventsUiState.ScreenState.LOADING
import pl.adrianczerwinski.events.EventsUiState.ScreenState.SUCCESS
import pl.adrianczerwinski.ui.components.CommonError
import pl.adrianczerwinski.ui.components.GradientOverlay

@Composable
fun Events(
    viewModel: EventsViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    EventsScreen(uiState = uiState, onRetryPressed = { viewModel.getEvents() }) {
        // TODO add navigation do media player
    }
}

@Composable
private fun EventsScreen(
    uiState: EventsUiState,
    onRetryPressed: () -> Unit = {},
    onEventClick: (Int) -> Unit
) = Column(
    modifier = Modifier.fillMaxSize()
) {
    when (uiState.screenState) {
        SUCCESS -> EventsList(uiState.events) { onEventClick(it) }

        LOADING -> Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(modifier = Modifier.size(60.dp))
        }

        ERROR -> CommonError { onRetryPressed() }
    }
}

@Composable
private fun EventsList(
    eventsList: List<EventUiModel>,
    onEventClick: (Int) -> Unit = {}
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(eventsList.size) { index ->
            Card(modifier = Modifier
                .padding(12.dp)
                .clickable { onEventClick(index) }) {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.25f)
                ) {
                    var isImageLoaded by remember {
                        mutableStateOf(false)
                    }

                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.25f),
                        model = eventsList[index].imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    ) {
                        when (painter.state) {
                            is Success -> {
                                isImageLoaded = true
                                SubcomposeAsyncImageContent()
                            }
                            else -> Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator(modifier = Modifier.size(60.dp))
                            }
                        }
                    }
                    if (isImageLoaded) {
                        GradientOverlay(maxHeight.value * 2)
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(12.dp)
                        ) {
                            Text(text = eventsList[index].title, style = MaterialTheme.typography.labelMedium, color = Color.White)
                            Text(text = eventsList[index].subtitle, style = MaterialTheme.typography.labelSmall, color = Color.White)
                        }
                        Text(
                            text = eventsList[index].date,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(12.dp)
                        )
                    }
                }
            }
        }
    }
}
