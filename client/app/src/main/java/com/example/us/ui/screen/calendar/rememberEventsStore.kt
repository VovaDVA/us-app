package com.example.us.ui.screen.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberEventsStore(): EventsStore {
    val context = LocalContext.current
    return remember { EventsStore(context) }
}