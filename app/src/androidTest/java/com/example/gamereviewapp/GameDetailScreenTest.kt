package com.example.gamereviewapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.gamereviewapp.ui.theme.GameDetailScreen
import model.Game
import org.junit.Rule
import org.junit.Test
import android.os.Build
import androidx.annotation.RequiresApi

class GameDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val testGame = Game(
        gameID = 1,
        gameTitle = "Elden Ring",
        gameCover = "https://image.api.playstation.com/vulcan/img/rnd/202111/0506/hcFeWRVGHYK72uOw6Mn6f4Ms.jpg",
        description = "An open world action RPG...",
        averageStars = 4.8,
        genre = "Action RPG",
        devloper = "FromSoftware",
        publisher = "Bandai Namco",
        releaseDate = "2022-02-25T00:00:00",
        platforms = "PS5, Xbox Series X, PC",
        multiplayer = true,
        socialMedia = "@EldenRing",
        controllerType = "DualShock, Xbox Controller",
        languages = "English, Japanese",
        requirements = "Requires 60 GB space",
        gameplayImages = "https://www.godisageek.com/wp-content/uploads/Elden-Ring-combat.jpg",
        price = 59.99,
        reviews = emptyList()
    )

    @Test
    fun gameDetailScreen_displaysAllGameInfo() {
        composeTestRule.setContent {
            GameDetailScreen(game = testGame)
        }

        // Assert key text elements are displayed
        composeTestRule.onNodeWithText("Elden Ring").assertIsDisplayed()
        composeTestRule.onNodeWithText("Genre: Action RPG").assertIsDisplayed()
        composeTestRule.onNodeWithText("Developer: FromSoftware").assertIsDisplayed()
        composeTestRule.onNodeWithText("Publisher: Bandai Namco").assertIsDisplayed()
        composeTestRule.onNodeWithText("Platforms: PS5, Xbox Series X, PC").assertIsDisplayed()
        composeTestRule.onNodeWithText("Release Date: 2022-02-25").assertIsDisplayed()
        composeTestRule.onNodeWithText("Description:").assertIsDisplayed()
        composeTestRule.onNodeWithText("An open world action RPG...").assertIsDisplayed()
        composeTestRule.onNodeWithText("Price: \$59.99").assertIsDisplayed()
    }
}
