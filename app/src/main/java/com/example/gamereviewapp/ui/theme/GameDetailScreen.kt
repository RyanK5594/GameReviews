package com.example.gamereviewapp.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import model.Game

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GameDetailScreen(game: Game, onBackClick: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(rememberScrollState())
    ) {

        AsyncImage(
            model = game.gameCover,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(MaterialTheme.shapes.medium)
        )

        OutlinedButton(
            onClick = onBackClick,
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.Start)
        ) {
            Text("Back")
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = game.gameTitle,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = MaterialTheme.shapes.small
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                DetailRow("Genre", game.genre)
                DetailRow("Developer", game.devloper)
                DetailRow("Publisher", game.publisher)
                DetailRow("Platforms", game.platforms)
                DetailRow("Release Date", game.releaseDate.take(10))

                Spacer(Modifier.height(12.dp))

                Text("Description:", style = MaterialTheme.typography.titleMedium)
                Text(game.description)

                Spacer(Modifier.height(12.dp))

                Text("Price: $${game.price}", style = MaterialTheme.typography.bodyLarge)
            }
        }

        Spacer(Modifier.height(12.dp))

        ReviewSection(gameID = game.gameID)
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(2f)
        )
    }
}
