package com.example.us.ui.screen.games

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun GamesNavHost(navController: NavHostController, hideBottomBar: (Boolean) -> Unit) {
    NavHost(
        navController = navController,
        startDestination = "games_list"
    ) {
        composable("games_list") {
            GamesListScreen(
                onOpenHeartClicker = { navController.navigate("heart_clicker") }
            )
        }
        composable("heart_clicker") {
            HeartClickerScreen(
                onBack = { navController.popBackStack() },
                hideBottomBar = hideBottomBar
            )
        }
    }
}

