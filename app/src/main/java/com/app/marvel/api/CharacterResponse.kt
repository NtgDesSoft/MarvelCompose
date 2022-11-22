package com.app.marvel.api

import java.io.Serializable

data class CharacterResponse(
    val data: Data,
    val status: String
)

data class Data(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<CharacterDetail>,
    val total: Int
)

data class CharacterDetail(
    val description: String,
    val id: Int,
    val name: String,
    val thumbnail: Thumbnail,
    val resourceURI: String
)

data class Thumbnail(
    val extension: String,
    val path: String,
) : Serializable