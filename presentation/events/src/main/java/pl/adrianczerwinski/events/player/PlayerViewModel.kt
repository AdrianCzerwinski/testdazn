package pl.adrianczerwinski.events.player

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class EventsUiState(
    val position: Long = 0L
)
@HiltViewModel
class PlayerViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(EventsUiState())
    val state: StateFlow<EventsUiState> = _state.asStateFlow()

    fun savePlayerPosition(position: Long) {
        _state.value = _state.value.copy(position = position)
    }
}
