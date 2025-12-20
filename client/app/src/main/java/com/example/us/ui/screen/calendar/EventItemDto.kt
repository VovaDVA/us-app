package com.example.us.ui.screen.calendar

import kotlinx.serialization.Serializable

@Serializable
data class EventItemDto(
    val id: Long? = null,
    val date: String,   // "yyyy-MM-dd"
    val text: String,
    val icon: String = "🎀",
    val authorId: Long? = null   // опционально, кто создал
)
