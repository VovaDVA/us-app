package com.example.us.ui.screen.special.classes

import kotlinx.serialization.Serializable

@Serializable
data class Wish(
    val id: Long = System.currentTimeMillis(),
    val text: String,
    val description: String = "",
    val link: String? = null,
    val importance: Int = 0,
    val categoryIcon: String = "★",
    val isDone: Boolean = false,
    val isFavorite: Boolean = false
)
