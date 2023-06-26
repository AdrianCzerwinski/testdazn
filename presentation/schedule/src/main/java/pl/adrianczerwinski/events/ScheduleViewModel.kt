package pl.adrianczerwinski.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.adrianczerwinski.common.datetime.Clock
import pl.adrianczerwinski.events.ScheduledEventsUiState.ScreenState.CONTENT
import pl.adrianczerwinski.events.ScheduledEventsUiState.ScreenState.ERROR
import pl.adrianczerwinski.events.ScheduledEventsUiState.ScreenState.LOADING
import javax.inject.Inject

data class ScheduledEventsUiState(
    val scheduledEvents: List<ScheduledEventUiModel> = listOf(),
    val screenState: ScreenState = LOADING
) {
    enum class ScreenState { CONTENT, LOADING, ERROR }
}

@HiltViewModel
class ScheduledEventsViewModel @Inject constructor(
    private val getScheduleUseCase: GetScheduleUseCase,
    private val mapper: ScheduleUiMapper,
    private val clock: Clock
) : ViewModel() {

    private var isActive = false

    private val _state = MutableStateFlow(ScheduledEventsUiState())
    val state: StateFlow<ScheduledEventsUiState> = _state.asStateFlow()

    init {
        isActive = true
        getScheduledEvents()
    }

    fun getScheduledEvents() = viewModelScope.launch {
        _state.value = _state.value.copy(screenState = LOADING)

        while (isActive) {
            val scheduledEvents = getScheduleUseCase.invoke()
            _state.value = if (scheduledEvents.isSuccess) {
                val newEvents = scheduledEvents
                    .getOrThrow()
                    .filter { clock.isTomorrow(it.date) }
                    .map { mapper.toUiModel(it) }
                    .sortedBy { it.date }
                _state.value.copy(scheduledEvents = newEvents, screenState = CONTENT)
            } else {
                _state.value.copy(screenState = ERROR)
            }
            delay(REQUEST_INTERVAL)
        }
    }

    fun onStop() {
        isActive = false
    }
}

private const val REQUEST_INTERVAL = 30000L
