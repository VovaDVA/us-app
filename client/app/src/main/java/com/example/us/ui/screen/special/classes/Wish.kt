package com.example.us.ui.screen.special.classes

import kotlinx.serialization.Serializable

@Serializable
data class Wish(
    val id: Long = System.currentTimeMillis(),
    val title: String = "",
    val description: String = "",
    val link: String? = null,
    val categoryIcon: String = "★",
    val isDone: Boolean = false,
    val isFavorite: Boolean = false
)

@Serializable
data class AddWishRequest(
    val title: String = "",
    val description: String = "",
    val link: String? = null,
    val categoryIcon: String = "★",
    val isFavorite: Boolean = false
)

fun Wish.toCreateRequest(): Map<String, Any?> = mapOf(
    "title" to title,
    "description" to description,
    "link" to link,
    "categoryIcon" to categoryIcon,
    "isFavorite" to isFavorite
)

