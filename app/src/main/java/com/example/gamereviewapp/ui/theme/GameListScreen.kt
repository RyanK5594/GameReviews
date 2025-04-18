package com.example.gamereviewapp.ui.theme

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.gamereviewapp.viewmodel.ViewGameModel

@Composable
fun GameListScreen(viewModel: ViewGameModel, modifier: Modifier = Modifier) {
    val games by viewModel.games.collectAsState()
    Log.d("GameListScreen", "Fetched games: $games")

    Column(modifier = modifier) {
        if (games.isEmpty()) {
            Text("Loading games...")
            CircularProgressIndicator()
        } else {
            LazyColumn {
                items(games) { game -> Text(text = "${game.gameTitle} - ${game.genre}")
                }
            }
        }
    }
}
