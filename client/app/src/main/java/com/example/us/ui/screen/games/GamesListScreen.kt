package com.example.us.ui.screen.games

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.us.ui.component.AnimatedLoveBackground

@Composable
fun GamesListScreen(onOpenHeartClicker: () -> Unit) {
    AnimatedLoveBackground(Modifier.fillMaxSize())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        GamesHeader()

        Spacer(Modifier.height(20.dp))

        GameCard(
            title = "Сердечный Кликер",
            description = "Нажимай на сердце, повышай ранг любви ❤️",
            onClick = onOpenHeartClicker
        )
    }
}
