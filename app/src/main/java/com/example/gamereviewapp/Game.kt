package com.example.gamereviewapp.model

import com.example.gamereviewapp.Review

data class Game(
    val gameID: Int,
    val gameTitle: String,
    val gameCover: String,
    val description: String,
    val averageStars: Double,
    val genre: String,
    val devloper: String,
    val publisher: String,
    val releaseDate: String,
    val platforms: String,
    val multiplayer: Boolean,
    val socialMedia: String,
    val controllerType: String,
    val languages: String,
    val requirements: String,
    val gameplayImages: String,
    val price: Double,
    val reviews: List<Review>
)
