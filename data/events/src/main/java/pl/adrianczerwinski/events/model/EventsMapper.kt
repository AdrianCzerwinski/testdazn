package pl.adrianczerwinski.events.model

import pl.adrianczerwinski.events.network.model.EventResponse
import pl.adrianczerwinski.events.network.model.ScheduledEventResponse

class EventsMapper {
    fun toEvent(eventResponse: EventResponse) = with(eventResponse) {
        // TODO implemene datetime parsing
        Event(
            id = id,
            date = 56L,
            title = title,
            imageUrl = imageUrl,
            videoUrl = videoUrl,
            subtitle = subtitle
        )
    }

    fun toScheduledEvent(scheduledEventResponse: ScheduledEventResponse) = with(scheduledEventResponse) {
        // TODO implemene datetime parsing
        ScheduledEvent(
            id = id,
            date = 56L,
            title = title,
            imageUrl = imageUrl,
            subtitle = subtitle
        )
    }
}
