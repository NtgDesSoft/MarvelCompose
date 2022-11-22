package com.app.marvel.api

import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstanceTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var apiService: CharacterEndPoints
    lateinit var gson: Gson

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        gson = Gson()
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(CharacterEndPoints::class.java)
    }

    @Test
    fun `get all movie api test`() {
        runBlocking {
            val mockResponse = MockResponse()
            mockWebServer.enqueue(mockResponse.setBody("{}"))
            val response = apiService.getAllCharacters(10)
            val request = mockWebServer.takeRequest()
            assertEquals("/characters?ts=1&apikey=678c0bb6d6aea2fbde954e9c8e170586&hash=6c5502e0089e46828d91a0d16149b185&limit=10", request.path)
            assertEquals(true, response.body()?.data == null)
        }
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }
}