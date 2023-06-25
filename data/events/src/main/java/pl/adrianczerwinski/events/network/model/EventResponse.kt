package pl.adrianczerwinski.events.network.model

import com.google.gson.annotations.SerializedName

data class EventResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("subtitle")
    val subtitle: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("imageUrl")
    val imageUrl: String,

    @SerializedName("videoUrl")
    val videoUrl: String
)
