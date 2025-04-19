package com.example.gamereviewapp.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.gamereviewapp.model.Game

@Composable
fun GameDetailScreen(game: Game) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        AsyncImage(
            model = game.gameCover,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(Modifier.height(16.dp))

        Text(game.gameTitle, style = MaterialTheme.typography.headlineMedium)
        Text("Genre: ${game.genre}")
        Text("Developer: ${game.devloper}")
        Text("Publisher: ${game.publisher}")
        Text("Platforms: ${game.platforms}")
        Text("Release Date: ${game.releaseDate.take(10)}")

        Spacer(Modifier.height(12.dp))

        Text("Description:", style = MaterialTheme.typography.titleMedium)
        Text(game.description)

        Spacer(Modifier.height(12.dp))

        Text("Price: $${game.price}")
    }
}