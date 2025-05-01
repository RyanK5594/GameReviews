package com.example.gamereviewapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import model.Game
import viewmodel.IViewGameModel
import com.example.gamereviewapp.ui.theme.GameListScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// Define an interface for ViewGameModel
interface IViewGameModel {
    val games: StateFlow<List<Game>>
}

class GameListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: IViewGameModel // Use the interface type

    private val testGames: List<Game> = listOf(
        Game(
            gameID = 1,
            gameTitle = "Monster Hunter Wilds",
            gameCover = "https://example.com/image.jpg",
            description = "The unbridled force of nature...",
            averageStars = 5.0,
            genre = "Action RPG",
            devloper = "Capcom",
            publisher = "Capcom",
            releaseDate = "2025-02-28T11:39:24.16",
            platforms = "PS5, Xbox Series X, PC",
            multiplayer = true,
            socialMedia = "@MonsterHunterGame",
            controllerType = "DualShock, Xbox Controller",
            languages = "English, Spanish",
            requirements = "Requires a 64-bit processor...",
            gameplayImages = "https://example.com/screenshot.jpg",
            price = 0.0,
            reviews = emptyList()
        )
    )

    // Fake ViewGameModel that implements the IViewGameModel interface
    class FakeViewGameModel(private val testData: List<Game>) : IViewGameModel {
        private val _games = MutableStateFlow(testData)
        override val games: StateFlow<List<Game>> get() = _games
    }

    @Before
    fun setup() {
        viewModel = FakeViewGameModel(testGames) // Use FakeViewGameModel
    }

    @Test
    fun displaysAllGamesCorrectly() {
        composeTestRule.setContent {
            GameListScreen(
                viewModel = viewModel,
                onGameClick = {},
                onLoginClick = {},
                onSignUpClick = {},
                onLogoutClick = {},
                userEmail = "guest"
            )
        }

        // Ensure each game title is displayed correctly
        testGames.forEach { game ->
            composeTestRule.onNodeWithText(game.gameTitle).assertIsDisplayed()
        }
    }
}
