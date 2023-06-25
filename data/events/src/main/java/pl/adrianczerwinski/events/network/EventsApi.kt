package pl.adrianczerwinski.events.network

import pl.adrianczerwinski.events.network.model.EventResponse
import pl.adrianczerwinski.events.network.model.ScheduledEventResponse
import retrofit2.http.GET

interface EventsApi {

    @GET("/getEvents")
    suspend fun getEvents(): List<EventResponse>

    @GET("/getSchedule")
    suspend fun getSchedule(): List<ScheduledEventResponse>
}
