package com.example.gamereviewapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.gamereviewapp.model.game
import com.example.gamereviewapp.network.RetrofitInstance
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun GameDetailWrapper(gameID: Int) {
    var game by remember { mutableStateOf<game?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val games = RetrofitInstance.api.getGames()
            game = games.find { it.gameID == gameID }
        } catch (e: Exception) {
            error = e.message
        } finally {
            isLoading = false
        }
    }

    when {
        isLoading -> CircularProgressIndicator()
        error != null -> Text("Error loading game: $error")
        game != null -> GameDetailScreen(game = game!!)
        else -> Text("Game not found.")
    }
}
