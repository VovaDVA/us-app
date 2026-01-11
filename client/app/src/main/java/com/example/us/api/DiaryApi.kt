package com.example.us.api

import com.example.us.ui.screen.diary.DiaryEvent

object DiaryApi {

    suspend fun getAll(): List<DiaryEvent> =
        ApiClient.get("diary")

    suspend fun create(event: DiaryEvent): DiaryEvent =
        ApiClient.post("diary", event)

    suspend fun update(id: Long, event: DiaryEvent): DiaryEvent =
        ApiClient.put("diary/$id", event)

    suspend fun delete(id: Long) {
        ApiClient.delete<Unit>("diary/$id")
    }
}

