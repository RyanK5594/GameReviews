package com.example.gamereviewapp

data class Review(
    val reviewID: Int,
    val reviewText: String,
    val user: String,
    val stars: Double,
    val dateOfReview: String,
    val gameID: Int
)
