package com.example.us.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val userId: Long,
    val partnerId: Long?,
    val token: String,
    val name: String
)
