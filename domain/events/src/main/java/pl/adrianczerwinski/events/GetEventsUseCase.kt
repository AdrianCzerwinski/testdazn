package pl.adrianczerwinski.events

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pl.adrianczerwinski.events.model.Event
import java.lang.Exception
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val eventsRepository: EventsRepository
) {
    suspend operator fun invoke(): Result<List<Event>> = withContext(Dispatchers.IO) {
        try {
            Result.success(eventsRepository.getEvents())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
