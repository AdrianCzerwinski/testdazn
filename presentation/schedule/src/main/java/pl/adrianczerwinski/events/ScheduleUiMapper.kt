package pl.adrianczerwinski.events

import pl.adrianczerwinski.common.datetime.DateTimeConverter
import pl.adrianczerwinski.events.model.ScheduledEvent
import javax.inject.Inject

class ScheduleUiMapper @Inject constructor(
    private val dateTimeConverter: DateTimeConverter
) {
    fun toUiModel(event: ScheduledEvent) = ScheduledEventUiModel(
        id = event.id,
        imageUrl = event.imageUrl,
        date = dateTimeConverter.formatEpochToLocalDateString(event.date),
        title = event.title,
        subtitle = event.subtitle
    )
}
