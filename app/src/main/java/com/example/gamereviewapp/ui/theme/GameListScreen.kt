package com.example.gamereviewapp.ui.theme

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.gamereviewapp.model.Game
import com.example.gamereviewapp.viewmodel.ViewGameModel


@Composable
fun GameListScreen(
    viewModel: ViewGameModel,
    onGameClick: (Game) -> Unit,
    modifier: Modifier = Modifier
) {
    val games by viewModel.games.collectAsState()

    LazyColumn(modifier = modifier) {
        items(games) { game ->
                GameListItem(game = game, onClick = { onGameClick(game) })
        }
    }
}

@Composable
fun GameListItem(game: Game, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        AsyncImage(
            model = game.gameCover,
            contentDescription = "${game.gameTitle} Cover",
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp)
        )

        Column(modifier = Modifier.weight(3f)) {
            Text(text = game.gameTitle)
            Text(text = "Genre: ${game.genre}")
            Text(text = "⭐ ${game.averageStars}")
        }
    }
}