package com.example.gamereviewapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamereviewapp.model.game
import com.example.gamereviewapp.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewGameModel : ViewModel() {
    private val _games = MutableStateFlow<List<game>>(emptyList())
    val games: StateFlow<List<game>> = _games

    init {
        fetchGames()
    }

    private fun fetchGames() {
        viewModelScope.launch {
            try {
                Log.d("GameAPI", "Fetching games...")

                val games = RetrofitInstance.api.getGames()

                Log.d("GameAPI", "Fetched: $games")
                _games.value = games
            } catch (e: Exception) {
                Log.e("GameAPI", "Error: ${e.message}", e)
            }
        }
    }
}
