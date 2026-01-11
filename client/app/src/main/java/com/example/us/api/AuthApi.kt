package com.example.us.api

import com.example.us.dto.AuthResponse
import com.example.us.dto.RegisterRequest

object AuthApi {

    suspend fun register(request: RegisterRequest): AuthResponse =
        ApiClient.post("auth/register", request)
}
