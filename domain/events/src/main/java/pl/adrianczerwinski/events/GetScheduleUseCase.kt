package pl.adrianczerwinski.events

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pl.adrianczerwinski.events.model.ScheduledEvent
import java.lang.Exception
import javax.inject.Inject

class GetScheduleUseCase @Inject constructor(
    private val eventsRepository: EventsRepository
) {
    @Suppress("TooGenericExceptionCaught")
    suspend operator fun invoke(): Result<List<ScheduledEvent>> = withContext(Dispatchers.IO) {
        try {
            Result.success(eventsRepository.getScheduledEvents())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
