package com.example.us.ui.screen.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.us.api.AuthApi
import com.example.us.api.AuthHolder
import com.example.us.dto.RegisterRequest
import com.example.us.storage.AuthStorage
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val context: Context
) : ViewModel() {

    fun register(name: String) {
        viewModelScope.launch {
            try {
                val response = AuthApi.register(
                    RegisterRequest(name)
                )

                AuthStorage.saveAuth(
                    context = context,
                    token = response.token,
                    userId = response.userId,
                    partnerId = response.partnerId,
                    name = response.name
                )

                AuthHolder.token = response.token
            } catch (e: Exception) {
                // позже покажем ошибку
            }
        }
    }
}

