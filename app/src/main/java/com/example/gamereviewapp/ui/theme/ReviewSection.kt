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
import com.example.gamereviewapp.model.review
import com.example.gamereviewapp.network.RetrofitInstance

@Composable
fun ReviewSection(gameID: Int) {
    var reviews by remember { mutableStateOf<List<review>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

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
