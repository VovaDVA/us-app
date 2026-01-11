package com.example.us.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val name: String
)
