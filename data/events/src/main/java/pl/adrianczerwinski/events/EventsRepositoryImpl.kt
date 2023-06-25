package pl.adrianczerwinski.events

import pl.adrianczerwinski.events.model.Event
import pl.adrianczerwinski.events.model.ScheduledEvent
import pl.adrianczerwinski.events.network.EventsRemoteDataSource
import javax.inject.Inject

internal class EventsRepositoryImpl @Inject constructor(
    private val eventsRemoteDataSource: EventsRemoteDataSource
) : EventsRepository {
    override suspend fun getEvents(): List<Event> = eventsRemoteDataSource.getEvents()

    override suspend fun getScheduledEvents(): List<ScheduledEvent> = eventsRemoteDataSource.getScheduledEvents()
}
