package com.example.us

import UsApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.us.api.AuthHolder
import com.example.us.storage.AuthStorage
import com.example.us.ui.screen.auth.RegisterScreen
import com.example.us.ui.screen.auth.SplashScreen

@Composable
fun AppEntry() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var isAuthorized by remember { mutableStateOf<Boolean?>(null) }

    LaunchedEffect(Unit) {
        AuthStorage.tokenFlow(context).collect { token ->
            AuthHolder.token = token
            isAuthorized = token != null
        }
    }

    when (isAuthorized) {
        null -> SplashScreen()
        true -> UsApp()
        false -> RegisterScreen()
    }
}
