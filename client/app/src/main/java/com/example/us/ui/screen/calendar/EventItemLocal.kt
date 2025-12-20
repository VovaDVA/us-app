package com.example.us.ui.screen.calendar

import kotlinx.serialization.Serializable

@Serializable
data class EventItemLocal(
    val id: Long? = null,
    val text: String,
    val icon: String = "🎀"
)
