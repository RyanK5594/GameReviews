package model

data class NewReviewRequest(
    val reviewText: String,
    val user: String,
    val stars: Double,
    val gameID: Int
)