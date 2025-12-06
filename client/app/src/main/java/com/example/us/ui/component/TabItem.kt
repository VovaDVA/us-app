package com.example.us.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

data class TabItem(val title: String, val icon: @Composable () -> Unit)

object Icons {
    val Heart: @Composable () -> Unit = { IconPlaceholder("❤️") }
    val Diary: @Composable () -> Unit = { IconPlaceholder("📓") }
    val Calendar: @Composable () -> Unit = { IconPlaceholder("📅") }
    val Star: @Composable () -> Unit = { IconPlaceholder("⭐") }
    val Puzzle: @Composable () -> Unit = { IconPlaceholder("🧩") }
}

@Composable
fun IconPlaceholder(symbol: String) {
    Text(text = symbol, fontSize = 24.sp)
}
