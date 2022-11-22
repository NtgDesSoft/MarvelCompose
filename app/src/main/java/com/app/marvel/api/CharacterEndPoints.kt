package com.app.marvel.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val APIKey="&apikey=678c0bb6d6aea2fbde954e9c8e170586&"

interface CharacterEndPoints {
    @GET(
        "characters?ts=1" +
                APIKey + "hash=6c5502e0089e46828d91a0d16149b185"
    )
    suspend fun getAllCharacters(
        @Query("limit") limit: Int
    ): Response<CharacterResponse>// MarvelCharacterResponse

    @GET(
        "characters/{characterId}?ts=2" +
                APIKey + "hash=fdf9e251bd923dcc7599fb1f514e392e"
    )
    suspend fun getCharacter(
        @Path("characterId") id: Int
    ):Response<CharacterResponse> //MarvelCharacterResponse
}