package pl.adrianczerwinski.events

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import pl.adrianczerwinski.events.EventsUiState.ScreenState.CONTENT
import pl.adrianczerwinski.events.EventsUiState.ScreenState.ERROR
import pl.adrianczerwinski.events.model.Event
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
class EventsViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private val getEventUseCase: GetEventsUseCase = mockk()
    private val mapper: EventUiMapper = mockk()

    @MockK
    private val viewModel by lazy {
        EventsViewModel(
            getEventsUseCase = getEventUseCase,
            mapper = mapper
        )
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init()
        coEvery { getEventUseCase() } returns Result.success(mockedEvents)
        every { mapper.toUiModel(mockedEvents.first()) } returns mockedUiEvents.first()
        every { mapper.toUiModel(mockedEvents[1]) } returns mockedUiEvents[1]
    }

    @Test
    fun `test initial state`() = runTest {
        assertThat(viewModel.state.value).isEqualTo(getDefaultUiState())
    }

    @Test
    fun `test getEvents(), should update events value and screen state to CONTENT when getEventUseCase is successful`() = runTest {
        viewModel.getEvents()

        runCurrent()

        assertThat(viewModel.state.value).isEqualTo(getDefaultUiState().copy(events = mockedUiEvents, screenState = CONTENT))
    }

    @Test
    fun `test getEvents(), should update screen state to ERROR when getEventUseCase failed`() = runTest {
        coEvery { getEventUseCase() } returns Result.failure(Throwable())
        viewModel.getEvents()

        runCurrent()

        assertThat(viewModel.state.value).isEqualTo(getDefaultUiState().copy(screenState = ERROR))
    }

    @Test
    fun `test openVideoPlayer(), should navigate to video player screen`() = runTest {
        viewModel.actions.test {
            viewModel.getEvents()

            viewModel.openVideoPlayer(mockedUiEvents.first().videoUrl)
            runCurrent()

            assertThat(expectItem()).isEqualTo(EventsUiAction.OpenVideoPlayer(mockedUiEvents.first().videoUrl))
        }
    }
}

private fun getDefaultUiState() = EventsUiState(
    events = emptyList(),
    screenState = EventsUiState.ScreenState.LOADING
)

private val mockedUiEvents = listOf(
    EventUiModel(
        id = "1",
        title = "Title",
        subtitle = "Subtitle",
        imageUrl = "https://www.google.com",
        videoUrl = "https://www.google.com",
        date = "10:20"
    ),
    EventUiModel(
        id = "2",
        title = "Title 2",
        subtitle = "Subtitle 2",
        imageUrl = "https://www.dazn.com",
        videoUrl = "https://www.dazn.com",
        date = "Tomorrow"
    )
)

private val mockedEvents = listOf(
    Event(
        id = "1",
        title = "Title",
        subtitle = "Subtitle",
        imageUrl = "https://www.google.com",
        videoUrl = "https://www.google.com",
        date = 1234567890L
    ),
    Event(
        id = "2",
        title = "Title 2",
        subtitle = "Subtitle 2",
        imageUrl = "https://www.dazn.com",
        videoUrl = "https://www.dazn.com",
        date = 1234567880L
    )
)
