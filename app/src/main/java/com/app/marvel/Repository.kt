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
        val limit =if (BuildConfig.FETCH_LIMIT==0) limit else BuildConfig.FETCH_LIMIT
        return remoteData.getAllCharacters(limit)
    }
}