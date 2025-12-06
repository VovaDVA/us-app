package com.example.us.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun UsTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        content = content
    )
}
