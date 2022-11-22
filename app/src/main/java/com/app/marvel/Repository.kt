package com.app.marvel

import com.app.marvel.api.CharacterResponse
import com.app.marvel.api.RetrofitInstance
import retrofit2.Response

class Repository {
    suspend fun getAll(limit:Int): Response<CharacterResponse> { return RetrofitInstance.API.getAllCharacters(limit)}
}