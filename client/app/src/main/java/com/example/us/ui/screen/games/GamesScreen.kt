package com.example.us.ui.screen.games

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun GamesScreen(hideBottomBar: (Boolean) -> Unit = {}, onOpenHeartClicker: () -> Unit) {
    val navController = rememberNavController()
    GamesNavHost(navController = navController, hideBottomBar = hideBottomBar)
}
