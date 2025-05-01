package com.example.gamereviewapp.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import model.Game
import viewmodel.IViewGameModel
import viewmodel.ViewGameModel
import androidx.compose.runtime.*


@Composable
fun GameListScreen(
    viewModel: IViewGameModel,
    onGameClick: (Game) -> Unit,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier,
    userEmail: String?
) {
    val games by viewModel.games.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedStars by remember { mutableStateOf<Float?>(null) }
    var expanded by remember { mutableStateOf(false) }

    val isLoggedIn = userEmail != null && userEmail.lowercase() != "guest"

    val filteredGames = games.filter { game ->
        val matchesQuery = searchQuery.isBlank() ||
                game.gameTitle.contains(searchQuery, ignoreCase = true) ||
                game.genre.contains(searchQuery, ignoreCase = true)

        val matchesStars = selectedStars?.let { game.averageStars >= it } ?: true


        matchesQuery && matchesStars
    }

    Column(modifier = modifier.fillMaxSize()) {
        if (isLoggedIn) {
            Text(
                text = "Logged in as: $userEmail",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            )
            OutlinedButton(
                onClick = onLogoutClick,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text("Log Out")
            }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                ) {
                    OutlinedButton(onClick = onLoginClick) {
                        Text("Login")
                    }
                    OutlinedButton(onClick = onSignUpClick) {
                        Text("Sign Up")
                    }
                }
            }
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search by title or genre") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = selectedStars?.let { "⭐ $it+" } ?: "Filter by stars")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                val starOptions = (1..10).map { it * 0.5f } // [0.5, 1.0, ..., 5.0]

                DropdownMenuItem(
                    onClick = {
                        selectedStars = null
                        expanded = false
                    },
                    text = { Text("All Ratings") }
                )

                starOptions.forEach { stars ->
                    DropdownMenuItem(
                        onClick = {
                            selectedStars = stars
                            expanded = false
                        },
                        text = { Text("⭐ $stars+") }
                    )
                }
            }
        }
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(filteredGames) { game ->
                GameListItem(game = game, onClick = { onGameClick(game) })
            }
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