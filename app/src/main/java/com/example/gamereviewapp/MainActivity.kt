package com.example.gamereviewapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.gamereviewapp.ui.theme.GameListScreen
import com.example.gamereviewapp.ui.theme.GameReviewAppTheme
import com.example.gamereviewapp.viewmodel.ViewGameModel

class MainActivity : ComponentActivity() {
    private val viewModel: ViewGameModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GameReviewAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GameListScreen(viewModel = viewModel, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
