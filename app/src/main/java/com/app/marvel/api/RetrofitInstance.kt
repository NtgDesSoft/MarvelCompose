package com.app.marvel.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    const val baseUrl = "https://gateway.marvel.com:443/v1/public/"

    val API: CharacterEndPoints by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CharacterEndPoints::class.java)
    }
}