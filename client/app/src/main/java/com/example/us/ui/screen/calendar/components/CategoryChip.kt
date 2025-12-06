package com.example.us.ui.screen.calendar.components

import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CategoryChip(symbol: String, onPick: (String) -> Unit) {
    AssistChip(onClick = { onPick(symbol) }, label = { Text(symbol) })
}