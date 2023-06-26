package pl.adrianczerwinski.events

import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import pl.adrianczerwinski.common.datetime.Clock
import pl.adrianczerwinski.events.ScheduledEventsUiState.ScreenState.CONTENT
import pl.adrianczerwinski.events.ScheduledEventsUiState.ScreenState.ERROR
import pl.adrianczerwinski.events.model.ScheduledEvent

@OptIn(ExperimentalCoroutinesApi::class)
class ScheduledEventsViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private val getScheduleUseCase: GetScheduleUseCase = mockk()
    private val mapper: ScheduleUiMapper = mockk()
    private val clock: Clock = mockk()

    @MockK
    private val viewModel by lazy {
        ScheduledEventsViewModel(
            getScheduleUseCase = getScheduleUseCase,
            mapper = mapper,
            clock = clock
        )
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init()
        coEvery { getScheduleUseCase() } returns Result.success(mockedEvents)
        every { mapper.toUiModel(mockedEvents.first()) } returns mockedUiEvents.first()
        every { mapper.toUiModel(mockedEvents[1]) } returns mockedUiEvents[1]
        every { clock.isTomorrow(any()) } returns true
    }

    @Test
    fun `test initial state`() = runTest {
        viewModel.onStop()
        assertThat(viewModel.state.value).isEqualTo(getDefaultUiState())
    }

    @Test
    fun `test getScheduledEvents(), should update events value and screen state to CONTENT when getEventUseCase is successful`() =
        runTest {
            viewModel.getScheduledEvents()

            advanceTimeBy(60000L)
            viewModel.onStop()
            runCurrent()

            assertThat(viewModel.state.first()).isEqualTo(
                getDefaultUiState().copy(scheduledEvents = mockedUiEvents, screenState = CONTENT)
            )
        }

    @Test
    fun `test getScheduledEvents(), should update screen state to ERROR when getEventUseCase failed`() = runTest {
        coEvery { getScheduleUseCase() } returns Result.failure(Throwable())
        viewModel.getScheduledEvents()

        advanceTimeBy(60000L)
        viewModel.onStop()
        runCurrent()

        assertThat(viewModel.state.first()).isEqualTo(getDefaultUiState().copy(screenState = ERROR))
        coVerify { getScheduleUseCase() }
    }
}

private fun getDefaultUiState() = ScheduledEventsUiState(
    scheduledEvents = emptyList(),
    screenState = ScheduledEventsUiState.ScreenState.LOADING
)

private val mockedUiEvents = listOf(
    ScheduledEventUiModel(
        id = "1",
        title = "Title",
        subtitle = "Subtitle",
        imageUrl = "https://www.google.com",
        date = "10:20"
    ),
    ScheduledEventUiModel(
        id = "2",
        title = "Title 2",
        subtitle = "Subtitle 2",
        imageUrl = "https://www.dazn.com",

        date = "Tomorrow"
    )
)

private val mockedEvents = listOf(
    ScheduledEvent(
        id = "1",
        title = "Title",
        subtitle = "Subtitle",
        imageUrl = "https://www.google.com",
        date = 1234567890L
    ),
    ScheduledEvent(
        id = "2",
        title = "Title 2",
        subtitle = "Subtitle 2",
        imageUrl = "https://www.dazn.com",
        date = 1234567880L
    )
)
