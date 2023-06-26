package pl.adrianczerwinski.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.adrianczerwinski.events.EventsUiAction.OpenVideoPlayer
import pl.adrianczerwinski.events.EventsUiState.ScreenState.ERROR
import pl.adrianczerwinski.events.EventsUiState.ScreenState.LOADING
import pl.adrianczerwinski.events.EventsUiState.ScreenState.SUCCESS
import javax.inject.Inject

data class EventsUiState(
    val events: List<EventUiModel> = listOf(),
    val screenState: ScreenState = LOADING
) {
    enum class ScreenState { SUCCESS, LOADING, ERROR }
}

sealed class EventsUiAction {
    data class OpenVideoPlayer(val url: String) : EventsUiAction()
}

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val mapper: EventUiMapper
) : ViewModel() {

    private val _state = MutableStateFlow(EventsUiState())
    val state: StateFlow<EventsUiState> = _state.asStateFlow()

    private val _actions = MutableSharedFlow<EventsUiAction>()
    val actions: SharedFlow<EventsUiAction> = _actions.asSharedFlow()

    init {
        getEvents()
    }

    fun getEvents() = viewModelScope.launch {
        _state.value = _state.value.copy(screenState = LOADING)
        val events = getEventsUseCase.invoke()

        _state.value = if (events.isSuccess) {
            val newEvents = events
                .getOrThrow()
                .map { mapper.toUiModel(it) }
                .sortedBy { it.date }
            _state.value.copy(events = newEvents, screenState = SUCCESS)
        } else {
            _state.value.copy(screenState = ERROR)
        }
    }

    fun openVideoPlayer(url: String) = viewModelScope.launch {
        _actions.emit(OpenVideoPlayer(url))
    }
}
