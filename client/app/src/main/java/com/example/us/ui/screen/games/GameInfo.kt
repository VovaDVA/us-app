package com.example.us.ui.screen.games

import com.example.us.R

data class GameInfo(val id: String, val title: String, val subtitle: String, val iconRes: Int)

// Пример списка игр — попозже можно подгружать динамически
val defaultGames = listOf(
    GameInfo(
        "heart_clicker",
        "Сердечный кликер",
        "Нажимай на сердце — прокачивай статус",
        R.drawable.ic_heart
    ),
    GameInfo("puzzle_simple", "Романтический пазл", "Сложи картинку вместе", R.drawable.ic_puzzle)
)
