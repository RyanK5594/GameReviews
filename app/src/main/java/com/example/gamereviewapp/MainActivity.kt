package com.example.gamereviewapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.gamereviewapp.ui.theme.GameReviewAppTheme
import model.Game
import viewmodel.ViewGameModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gamereviewapp.ui.theme.GameListScreen
import com.example.gamereviewapp.ui.theme.GameDetailWrapper
import com.example.gamereviewapp.ui.theme.LoginScreen
import com.example.gamereviewapp.ui.theme.SignUpScreen
import com.google.firebase.auth.FirebaseAuth


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth = FirebaseAuth.getInstance()

                setContent {
                    val navController = rememberNavController()
                    val viewModel = remember { ViewGameModel() }

                    GameReviewAppTheme {
                        NavHost(navController = navController, startDestination = "gameList") {
                            composable("gameList") {
                                val currentUser = auth.currentUser
                                val userEmail = currentUser?.email ?: "Guest"

                                GameListScreen(
                                    viewModel = viewModel,
                                    userEmail = userEmail,
                                    onGameClick = { game ->
                                        navController.navigate("gameDetail/${game.gameID}")
                                    },
                                    onLoginClick = {
                                        navController.navigate("login")
                                    },
                                    onSignUpClick = {
                                        navController.navigate("signup")
                                    },
                                    onLogoutClick = {
                                        auth.signOut()
                                        navController.navigate("gameList") {
                                            popUpTo("gameList") { inclusive = true }
                                        }
                                    }
                                )
                            }
                            composable("gameDetail/{gameId}") { backStackEntry ->
                                val gameId = backStackEntry.arguments?.getString("gameId")?.toIntOrNull()
                                if (gameId != null) {
                                    GameDetailWrapper(gameID = gameId)
                                }
                            }
                            composable("login") {
                                LoginScreen(
                                    auth = auth,
                                    onSignUpClick = { navController.navigate("signup") },
                                    onLoginSuccess = { userEmail ->
                                        navController.navigate("gameList/$userEmail")}
                                )
                            }
                            composable("signup") {
                                SignUpScreen(
                                    auth = auth,
                                    onLoginClick = { navController.navigate("login") },
                                    onSignUpSuccess = { navController.navigate("gameList") }
                                )
                            }
                        }
                    }
             0   }
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
