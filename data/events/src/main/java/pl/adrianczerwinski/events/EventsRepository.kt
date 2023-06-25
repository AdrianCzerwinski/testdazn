package pl.adrianczerwinski.events

import pl.adrianczerwinski.events.model.Event
import pl.adrianczerwinski.events.model.ScheduledEvent

interface EventsRepository {
    suspend fun getEvents(): List<Event>
    suspend fun getScheduledEvents(): List<ScheduledEvent>
}
