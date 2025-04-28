package com.example.gamereviewapp.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gamereviewapp.Review
import com.example.gamereviewapp.network.RetrofitInstance
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.rememberCoroutineScope
import com.example.gamereviewapp.NewReviewRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReviewSection(gameID: Int) {
    var reviews by remember { mutableStateOf<List<Review>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    var starRating by remember { mutableStateOf(0f) }
    var reviewText by remember { mutableStateOf("") }

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

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

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp).verticalScroll(rememberScrollState())) {
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
        Spacer(Modifier.height(24.dp))

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
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            decorationBox = @Composable { innerTextField ->
                Box(modifier = Modifier.border(1.dp, Color.Gray).padding(8.dp)) {
                    if (reviewText.isEmpty()) {
                        Text("Write a review...", color = Color.Gray)
                    }
                    innerTextField()
                }
            }
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                if (reviewText.isNotEmpty() && starRating > 0) {
                    val newReviewRequest = NewReviewRequest(
                        reviewText = reviewText,
                        user = FirebaseAuth.getInstance().currentUser?.email ?: "Anonymous",
                        stars = starRating.toDouble(),
                        gameID = gameID
                    )
                    coroutineScope.launch {
                        try {
                            RetrofitInstance.api.postReview(newReviewRequest)
                            // Locally add the review with dummy ID and current time
                            val newReview = Review(
                                reviewID = (reviews.maxOfOrNull { it.reviewID } ?: 0) + 1,
                                reviewText = reviewText,
                                user = newReviewRequest.user,
                                stars = newReviewRequest.stars,
                                dateOfReview = LocalDateTime.now().toString(),
                                gameID = newReviewRequest.gameID
                            )
                            reviews = reviews + newReview
                            starRating = 0f
                            reviewText = ""
                            Toast.makeText(context, "Review submitted!", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error submitting review: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Please enter a review and rating.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit Review")
        }
    }
}

fun submitReview(newReview: Review) {
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