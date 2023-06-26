package pl.adrianczerwinski.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
@HiltViewModel
class EventsViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val mapper: EventUiMapper
) : ViewModel() {

    private val _state = MutableStateFlow(EventsUiState())
    val state: StateFlow<EventsUiState> = _state.asStateFlow()
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
}
