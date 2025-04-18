package com.example.gamereviewapp.network

import com.example.gamereviewapp.model.game
import retrofit2.http.GET

interface GameApi {
    @GET("api/games")
    suspend fun getGames(): List<game>
}
