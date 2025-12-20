package com.example.us.api

import com.example.us.ui.screen.calendar.EventItemDto
import com.example.us.ui.screen.calendar.NextEventDto

object EventApi {
    // Получить все события для пары (передаём userId и partnerId)
    suspend fun getAll(userId: Long, partnerId: Long): List<EventItemDto> =
        ApiClient.get("events?userId=$userId&partnerId=$partnerId")

    suspend fun getNext(userId: Long, partnerId: Long): NextEventDto? =
        ApiClient.get("events/next?userId=$userId&partnerId=$partnerId")

    // Создать событие
    suspend fun create(userId: Long, partnerId: Long, req: EventItemDto): EventItemDto =
        ApiClient.post("events?userId=$userId&partnerId=$partnerId", req)

    // Обновить событие
    suspend fun update(userId: Long, partnerId: Long, id: Long, req: EventItemDto): EventItemDto =
        ApiClient.put("events/$id?userId=$userId&partnerId=$partnerId", req)

    // Удалить событие
    suspend fun delete(userId: Long, partnerId: Long, id: Long) {
        ApiClient.delete<Unit>("events/$id?userId=$userId&partnerId=$partnerId")
    }
}
