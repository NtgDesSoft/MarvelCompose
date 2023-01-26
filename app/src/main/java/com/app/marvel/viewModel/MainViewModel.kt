package com.app.marvel.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.marvel.Repository
import com.app.marvel.data.CharacterInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _APIState = MutableStateFlow<APITask>(APITask.Init)
    val APIState : StateFlow<APITask> = _APIState.asStateFlow()

    init {
        getAll()
    }

    fun getAll(limit:Int=90){
        _APIState.value = APITask.Loading

        viewModelScope.launch {
            val response = repository.getAll(limit)

            if (response.isSuccessful) {
                _APIState.value = APITask.Response.Ok(
                    response.body()?.let {
                        it.data.results.map {
                            CharacterInfo(
                                Name = it.name,
                                Details = it.description,
                                imageUrl = it.thumbnail.path+"/portrait_small."+it.thumbnail.extension
                            )
                        }
                    } ?: emptyList()
                )
            } else _APIState.value = APITask.Response.error(response.message())
        }
    }
}