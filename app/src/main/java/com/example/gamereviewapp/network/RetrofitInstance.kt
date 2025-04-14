package com.example.gamereviewapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: GameApi = Retrofit.Builder()
        .baseUrl("ReplaceWithDeployedAPI")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GameApi::class.java)
}