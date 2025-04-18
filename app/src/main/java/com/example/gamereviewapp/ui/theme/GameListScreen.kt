package com.example.gamereviewapp.ui.theme

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gamereviewapp.model.game
import com.example.gamereviewapp.viewmodel.ViewGameModel

@Composable
fun GameListScreen(
    viewModel: ViewGameModel,
    onGameClick: (game) -> Unit,
    modifier: Modifier = Modifier
) {
    val games by viewModel.games.collectAsState()

    LazyColumn(modifier = modifier) {
        items(games) { game ->
            Text(
                text = "${game.gameTitle} - ${game.genre}",
                modifier = Modifier
                    .clickable { onGameClick(game) }
                    .padding(16.dp)
            )
        }
    }
}
