package pl.adrianczerwinski.events.model

data class ScheduledEvent(
    val id: String,
    val title: String,
    val subtitle: String,
    val date: Long,
    val imageUrl: String
)
