package com.app.marvel.viewModel

import com.app.marvel.RepositoryImpl
import com.app.marvel.api.CharacterDetail
import com.app.marvel.api.CharacterResponse
import com.app.marvel.api.Data
import com.app.marvel.api.Thumbnail
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class MainViewModelTest {
    private val testDispatcher = TestCoroutineDispatcher()
    lateinit var viewModel: MainViewModel
    lateinit var repository: RepositoryImpl

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = MainViewModel(repository)
    }

    @Test
    fun `get data`() {
        runBlocking {
            coEvery { repository.getAll(any()) } returns (Response.success(characterResponse))

            viewModel.getAll(90)

            assertThat((viewModel.APIState.value as APITask.Response.Ok).payload.size).isGreaterThan(0)
        }
    }

    @Test
    fun `get character data`() {
        runBlocking {
            coEvery { repository.getAll(any()) } returns (Response.success(characterResponse))

            viewModel.getAll()

            assertThat((viewModel.APIState.value as APITask.Response.Ok).payload[0].Name).isEqualTo(characterA.name)
        }
    }

    @Test
    fun `get empty list if remote data available`() {
        runBlocking {
            coEvery { repository.getAll(any()) } returns (Response.success(characterResponse.copy(data = dataEmpty)))

            viewModel.getAll()

            coVerify { repository.getAll(any()) }

            assertThat(viewModel.APIState.value).isEqualTo(APITask.Response.Ok(emptyList()))
        }
    }

    @Test
    fun `Verify that get all was called with limit 5`() {
        runBlocking {
            coEvery { repository.getAll(any()) } returns (Response.success(characterResponse.copy(data = dataEmpty)))

            viewModel.getAll(5)

            coVerify { repository.getAll(5) }
        }
    }

}

val characterA = CharacterDetail(
    description = "some information",
    id = 2,
    name = "characterA=Bomb",
    thumbnail = Thumbnail("", ""),
    resourceURI = ""
)

val data = Data(
    count = 1,
    limit = 90,
    offset = 0,
    total = 1,
    results = listOf(characterA)
)

val dataEmpty = Data(
    count = 0,
    limit = 90,
    offset = 0,
    total = 0,
    results = emptyList()
)

val characterResponse = CharacterResponse(
    data = data,
    status = ""
)