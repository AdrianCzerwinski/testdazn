package pl.adrianczerwinski.events.model

import pl.adrianczerwinski.common.convertDateToEpochAndToLocal
import pl.adrianczerwinski.events.network.model.EventResponse
import pl.adrianczerwinski.events.network.model.ScheduledEventResponse
import javax.inject.Inject

class EventsMapper @Inject constructor() {
    fun toEvent(eventResponse: EventResponse) = with(eventResponse) {
        Event(
            id = id,
            date = convertDateToEpochAndToLocal(date),
            title = title,
            imageUrl = imageUrl,
            videoUrl = videoUrl,
            subtitle = subtitle
        )
    }

    fun toScheduledEvent(scheduledEventResponse: ScheduledEventResponse) = with(scheduledEventResponse) {
        ScheduledEvent(
            id = id,
            date = convertDateToEpochAndToLocal(date),
            title = title,
            imageUrl = imageUrl,
            subtitle = subtitle
        )
    }
}
