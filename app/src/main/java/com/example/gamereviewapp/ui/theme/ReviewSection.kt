package com.example.gamereviewapp.ui.theme

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.example.gamereviewapp.model.review
import com.example.gamereviewapp.network.RetrofitInstance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReviewSection(gameID: Int) {
    var reviews by remember { mutableStateOf<List<review>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var starRating by remember { mutableStateOf(0f) } // Rating from 0 to 5
    var reviewText by remember { mutableStateOf("") }  // Review text input

    LaunchedEffect(Unit) {
        try {
            val allReviews = RetrofitInstance.api.getReviews()
            reviews = allReviews.filter { it.gameID == gameID }
        } catch (e: Exception) {
            error = e.message
        } finally {
            isLoading = false
        }
    }

        Spacer(Modifier.height(16.dp))

        Text("Leave a Review", style = MaterialTheme.typography.titleMedium)
        Text("Rating: ${"%.1f".format(starRating)}")

        Slider(
            value = starRating,
            onValueChange = { starRating = it },
            valueRange = 0f..5f,
            steps = 4,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        BasicTextField(
            value = reviewText,
            onValueChange = { reviewText = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.border(1.dp, Color.Gray)) {
                    if (reviewText.isEmpty()) {
                        Text("Write a review...", color = Color.Gray, modifier = Modifier.padding(8.dp))
                    }
                    innerTextField()
                }
            }
        )

        Spacer(Modifier.height(8.dp))

        val context = LocalContext.current
        Button(
            onClick = {
                if (reviewText.isNotEmpty() && starRating > 0) {
                    val newReview = review(
                        reviewID = (reviews.maxOfOrNull { it.reviewID } ?: 0) + 1,  // Increment reviewID
                        reviewText = reviewText,
                        user = FirebaseAuth.getInstance().currentUser?.email ?: "Anonymous",
                        stars = starRating,
                        dateOfReview = LocalDateTime.now().toString(),
                        gameID = gameID
                    )
                    submitReview(newReview)
                    reviews = reviews + newReview
                    starRating = 0f
                    reviewText = ""
                    Toast.makeText(context, "Review submitted!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Please enter a review and rating.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit Review")
        }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Reviews", style = MaterialTheme.typography.titleLarge)

        when {
            isLoading -> CircularProgressIndicator()
            error != null -> Text("Failed to load reviews: $error")
            reviews.isEmpty() -> Text("No reviews yet.")
            else -> {
                reviews.forEach { review ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = review.user, style = MaterialTheme.typography.labelLarge)
                            Text(text = "⭐️ ${review.stars}")
                            Text(text = review.reviewText)
                        }
                    }
                }
            }
        }
    }
}

fun submitReview(newReview: review) {
    val db = FirebaseFirestore.getInstance()
    val reviewsRef = db.collection("reviews")

    reviewsRef.add(newReview)
        .addOnSuccessListener {
            println("Review submitted successfully!")
        }
        .addOnFailureListener { e ->
            println("Error submitting review: $e")
        }
}