package com.example.gamereviewapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitInstance {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://gamereviewsapi20250417215009.azurewebsites.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: GameApi by lazy {
        retrofit.create(GameApi::class.java)
    }
}