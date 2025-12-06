package com.example.us.ui.screen.home.utils

import androidx.compose.ui.graphics.Color

// --------------------------- Эмоции ---------------------------
enum class MoodType {
    HAPPY,
    SAD,
    ROMANTIC,
    FUNNY,
    SPICY_18,
    CALM,
    INSPIRED,
    SURPRISED,
    ANGRY,
    FEAR,
    PROUD,
    CURIOUS
}

// --------------------------- Цвета для эмоций ---------------------------
fun moodColor(type: MoodType): Color = when (type) {
    MoodType.HAPPY -> Color(0xFFFF9800)        // ярко-жёлтый
    MoodType.SAD -> Color(0xFF4E50FF)          // синий
    MoodType.ROMANTIC -> Color(0xFFFF6BAB)     // розовый
    MoodType.FUNNY -> Color(0xFF48D000)        // салатовый
    MoodType.SPICY_18 -> Color(0xFFFF4141)     // красный
    MoodType.CALM -> Color(0xFF7ED4FF)         // голубой
    MoodType.INSPIRED -> Color(0xFF00B99F)     // мятный
    MoodType.SURPRISED -> Color(0xFFAA00FF)    // фиолетовый
    MoodType.ANGRY -> Color(0xFF990000)        // тёмно-красный
    MoodType.FEAR -> Color(0xFF607D8B)         // серо-синий
    MoodType.PROUD -> Color(0xFF800080)        // пурпурный
    MoodType.CURIOUS -> Color(0xFF00BCD4)      // бирюзовый
}

fun moodIcon(type: MoodType): String = when(type) {
    MoodType.HAPPY -> "😄"
    MoodType.SAD -> "😢"
    MoodType.ROMANTIC -> "💖"
    MoodType.FUNNY -> "😂"
    MoodType.SPICY_18 -> "🔥"
    MoodType.CALM -> "🌿"
    MoodType.INSPIRED -> "✨"
    MoodType.SURPRISED -> "😲"
    MoodType.ANGRY -> "😡"
    MoodType.FEAR -> "😱"
    MoodType.PROUD -> "🏅"
    MoodType.CURIOUS -> "🧐"
}

// --------------------------- Русские названия эмоций ---------------------------
fun moodRussian(type: MoodType): String = when (type) {
    MoodType.HAPPY -> "Счастье"
    MoodType.SAD -> "Грусть"
    MoodType.ROMANTIC -> "Романтика"
    MoodType.FUNNY -> "Радость"
    MoodType.SPICY_18 -> "Секси"
    MoodType.CALM -> "Спокойствие"
    MoodType.INSPIRED -> "Воодушевление"
    MoodType.SURPRISED -> "Удивление"
    MoodType.ANGRY -> "Злость"
    MoodType.FEAR -> "Страх"
    MoodType.PROUD -> "Гордость"
    MoodType.CURIOUS -> "Любопытство"
}
