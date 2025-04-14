package com.example.gamereviewapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamereviewapp.model.game
import com.example.gamereviewapp.network.RetrofitInstance
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf

class ViewGameModel : ViewModel() {
    var games = mutableStateListOf<game>()
        private set

    init {
        fetchGames()
    }

    private fun fetchGames() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getGames()
                games.addAll(response)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
