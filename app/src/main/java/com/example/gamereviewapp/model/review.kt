package com.example.gamereviewapp.model

data class review(
    val reviewID: Int,
    val reviewText: String,
    val user: String,
    val stars: Int,
    val dateOfReview: String,
    val gameID: Int
)