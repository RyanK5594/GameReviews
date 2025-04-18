package com.example.gamereviewapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.gamereviewapp.ui.theme.GameReviewAppTheme
import com.example.gamereviewapp.network.RetrofitInstance
import com.example.gamereviewapp.model.Game

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var games by remember { mutableStateOf<List<Game>>(emptyList()) }
            var loading by remember { mutableStateOf(true) }
            val context = LocalContext.current

            LaunchedEffect(Unit) {
                try {
                    games = RetrofitInstance.api.getGames()
                    loading = false
                } catch (e: Exception) {
                    loading = false
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }

            GameReviewAppTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (loading) {
                        CircularProgressIndicator()
                    } else {
                        GameSwipeScreen(games)
                    }
                }
            }
        }
    }
}

@Composable
fun GameCard(game: Game) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(250.dp)
            .height(180.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = game.gameCover.ifEmpty { null },
                contentDescription = "Game Cover",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = game.gameTitle,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun GameSwipeScreen(games: List<Game>) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
    ) {
        games.forEach { game ->
            GameCard(game)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGameScreen() {
    val sampleGames = listOf(
        Game(
            gameID = 1,
            gameTitle = "MH Wilds",
            gameCover = "",  // You can put a sample URL here
            description = "",
            averageStars = 5.0,
            genre = "Action RPG",
            devloper = "Developer Name",
            publisher = "Publisher Name",
            releaseDate = "2025-04-18",
            platforms = "PC, PS5",
            multiplayer = true,
            socialMedia = "",
            controllerType = "Gamepad",
            languages = "English, Japanese",
            requirements = "8 GB RAM, 50 GB free space",
            gameplayImages = "",
            price = 59.99,
            reviews = listOf()  // Pass an empty list or sample reviews
        )
    )
    GameSwipeScreen(sampleGames)
}
