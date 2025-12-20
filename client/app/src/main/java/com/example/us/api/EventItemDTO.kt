package com.example.us.api

import com.example.us.ui.screen.calendar.EventItem
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class EventItemDTO(val text: String, val icon: String = "🎀")

object EventService {
    suspend fun getEvents(): Map<String, List<EventItemDTO>> =
        ApiClient.get("events")

    suspend fun addEvent(date: LocalDate, text: String, icon: String = "🎀") =
        ApiClient.post<Unit>("events", mapOf("date" to date.toString(), "text" to text, "icon" to icon))

    suspend fun updateEvent(date: LocalDate, index: Int, text: String, icon: String) =
        ApiClient.put<Unit>("events/$index", mapOf("date" to date.toString(), "text" to text, "icon" to icon))

    suspend fun removeEvent(date: LocalDate, index: Int) =
        ApiClient.delete<Unit>("events/$index?date=${date}")
}
