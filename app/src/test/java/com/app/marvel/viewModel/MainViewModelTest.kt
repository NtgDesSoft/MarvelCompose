package com.app.marvel.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.marvel.Repository
import com.app.marvel.api.CharacterResponse
import com.app.marvel.api.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    lateinit var viewModel: MainViewModel
    lateinit var repository: Repository

    @Mock
    lateinit var states: APITask

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        repository = Repository()
        viewModel = MainViewModel(repository)
    }


    @Test
    fun getAll() {
        runBlocking {
            Mockito.`when`(repository.getAll(90))
                .thenReturn(Response.success(characterResponse))

            viewModel.getAll(90)
            val result = viewModel.APIState.value
            assertEquals(
                result, APITask.Response.Ok(listOf())
            )
        }
    }

    @Test
    fun `getAll empty list`() {
        runBlocking {
            Mockito.`when`(repository.getAll(90))
                .thenReturn(Response.success(characterResponse))

            viewModel.getAll(90)
            val result = viewModel.APIState.value
            assertEquals(result, APITask.Response.Ok(emptyList()))
        }
    }

}


val data = Data(
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