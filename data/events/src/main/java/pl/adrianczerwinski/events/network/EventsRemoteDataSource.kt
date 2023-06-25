package pl.adrianczerwinski.events.network

import pl.adrianczerwinski.events.model.Event
import pl.adrianczerwinski.events.model.EventsMapper
import pl.adrianczerwinski.events.model.ScheduledEvent
import javax.inject.Inject

internal class EventsRemoteDataSource @Inject constructor(
    private val eventsApi: EventsApi,
    private val mapper: EventsMapper
) {
    suspend fun getEvents(): List<Event> = eventsApi.getEvents().map { mapper.toEvent(it) }

    suspend fun getScheduledEvents(): List<ScheduledEvent> = eventsApi.getSchedule().map { mapper.toScheduledEvent(it) }
}
