package com.example.us.ui.screen.diary

data class DiaryEvent(
    val id: Int,
    val title: String,
    val description: String,
    val date: Long,
    val imageUri: String?, // <-- строка
    val type: EventType
)

enum class EventType {
    HAPPY, ROMANTIC, SAD, IMPORTANT, FUNNY, SPICY_18
}
