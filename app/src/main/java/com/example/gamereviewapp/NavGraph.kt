package com.example.gamereviewapp.ui.navigation

sealed class Screen(val route: String) {
    object GameList : Screen("game_list")
    object GameDetail : Screen("game_detail/{gameId}") {
        fun createRoute(gameId: Int) = "game_detail/$gameId"
    }
}