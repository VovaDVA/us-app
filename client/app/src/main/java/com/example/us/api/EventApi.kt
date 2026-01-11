package com.example.us.api

import com.example.us.ui.screen.calendar.EventItemDto
import com.example.us.ui.screen.calendar.NextEventDto

object EventApi {

    suspend fun getAll(): List<EventItemDto> =
        ApiClient.get("events")

    suspend fun getNext(): NextEventDto? =
        ApiClient.get("events/next")

    suspend fun create(req: EventItemDto): EventItemDto =
        ApiClient.post("events", req)

    suspend fun update(id: Long, req: EventItemDto): EventItemDto =
        ApiClient.put("events/$id", req)

    suspend fun delete(id: Long) {
        ApiClient.delete<Unit>("events/$id")
    }
}

