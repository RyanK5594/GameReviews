package com.example.gamereviewapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import com.example.gamereviewapp.ui.theme.GameReviewAppTheme
import com.example.gamereviewapp.ui.theme.GameDetailWrapper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameReviewAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    GameDetailWrapper(gameID = 1)
                }
            }
        }
    }
}
