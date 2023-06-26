package pl.adrianczerwinski.events

import pl.adrianczerwinski.common.datetime.DateTimeConverter
import pl.adrianczerwinski.events.model.Event
import javax.inject.Inject

class EventUiMapper @Inject constructor(
    private val dateTimeConverter: DateTimeConverter
) {
    fun toUiModel(event: Event) = EventUiModel(
        id = event.id,
        imageUrl = event.imageUrl,
        videoUrl = event.videoUrl,
        date = dateTimeConverter.formatEpochToLocalDateString(event.date),
        title = event.title,
        subtitle = event.subtitle
    )
}
