package com.app.marvel

import com.app.marvel.api.CharacterEndPoints
import com.app.marvel.api.CharacterResponse
import retrofit2.Response
import javax.inject.Inject

interface Repository {
    suspend fun getAll(limit:Int): Response<CharacterResponse>
}

class RepositoryImpl @Inject constructor(
    private val remoteData: CharacterEndPoints
) : Repository {
    override suspend fun getAll(limit: Int): Response<CharacterResponse> {
        return remoteData.getAllCharacters(limit)
    }
}