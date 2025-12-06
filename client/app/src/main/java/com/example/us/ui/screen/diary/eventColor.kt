package com.example.us.ui.screen.diary

import androidx.compose.ui.graphics.Color

fun eventColor(type: EventType): Color = when (type) {
    EventType.ROMANTIC -> Color(0xFFFF6BAB)
    EventType.HAPPY -> Color(0xFFFFAE00)
    EventType.SAD -> Color(0xFF4E50FF)
    EventType.IMPORTANT -> Color(0xFF00B99F)
    EventType.FUNNY -> Color(0xFF48D000)
    EventType.SPICY_18 -> Color(0xFFFF4141)
}

fun eventTypeRussian(type: EventType): String = when (type) {
    EventType.ROMANTIC -> "Романтика"
    EventType.HAPPY -> "Счастье"
    EventType.SAD -> "Грусть"
    EventType.IMPORTANT -> "Важное"
    EventType.FUNNY -> "Радость"
    EventType.SPICY_18 -> "Секси"
}

