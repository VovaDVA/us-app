package com.example.us.ui.screen.diary

import kotlinx.serialization.Serializable

@Serializable
data class DiaryEvent(
    val id: Int,
    val title: String,
    val description: String,
    val date: Long,
    val imageSmallUrl: String?,
    val imageLargeUrl: String?,
    val type: EventType
)

enum class EventType {
    HAPPY, ROMANTIC, SAD, IMPORTANT, FUNNY, SPICY_18
}
