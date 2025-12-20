package com.example.us.ui.screen.calendar

import kotlinx.serialization.Serializable

@Serializable
data class NextEventDto(
    val id: Long,
    val date: String,   // "yyyy-MM-dd"
    val text: String,
    val icon: String = "🎀",
    val authorId: Long? = null
)
