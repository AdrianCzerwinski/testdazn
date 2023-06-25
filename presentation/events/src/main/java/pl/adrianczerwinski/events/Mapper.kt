package pl.adrianczerwinski.events

import pl.adrianczerwinski.common.formatEpochToLocalDateString
import pl.adrianczerwinski.events.model.Event

internal fun Event.toUiModel() = EventUiModel(
    id = this.id,
    imageUrl = this.imageUrl,
    videoUrl = this.videoUrl,
    date = formatEpochToLocalDateString(this.date),
    title = this.title,
    subtitle = this.subtitle
)