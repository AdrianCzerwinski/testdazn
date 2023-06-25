package pl.adrianczerwinski.events

data class EventUiModel(
    val id: String,
    val title: String,
    val subtitle: String,
    val date: String,
    val imageUrl: String,
    val videoUrl: String
)
