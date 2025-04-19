package com.example.gamereviewapp.network

import com.example.gamereviewapp.model.game
import com.example.gamereviewapp.model.review
import retrofit2.http.GET

interface GameApi {
    @GET("api/games")
    suspend fun getGames(): List<game>

    @GET("api/reviews")
    suspend fun getReviews(): List<review>
}
