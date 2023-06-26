package pl.adrianczerwinski.events.model

import pl.adrianczerwinski.common.datetime.DateTimeConverter
import pl.adrianczerwinski.events.network.model.EventResponse
import pl.adrianczerwinski.events.network.model.ScheduledEventResponse
import javax.inject.Inject

class EventsMapper @Inject constructor(
    private val dateTimeConverter: DateTimeConverter
) {
    fun toEvent(eventResponse: EventResponse) = with(eventResponse) {
        Event(
            id = id,
            date = dateTimeConverter.toEpochMilliseconds(date),
            title = title,
            imageUrl = imageUrl,
            videoUrl = videoUrl,
            subtitle = subtitle
        )
    }

    fun toScheduledEvent(scheduledEventResponse: ScheduledEventResponse) = with(scheduledEventResponse) {
        ScheduledEvent(
            id = id,
            date = dateTimeConverter.toEpochMilliseconds(date),
            title = title,
            imageUrl = imageUrl,
            subtitle = subtitle
        )
    }
}
