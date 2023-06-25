package pl.adrianczerwinski.events.model

data class Event(
    val id: String,
    val title: String,
    val subtitle: String,
    val date: Long,
    val imageUrl: String,
    val videoUrl: String
)
