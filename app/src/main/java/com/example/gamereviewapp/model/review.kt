package com.example.gamereviewapp.model

data class review(
    val reviewID: Int,
    val reviewText: String,
    val user: String,
    val stars: Float,
    val dateOfReview: String,
    val gameID: Int
)