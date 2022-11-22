package com.app.marvel.viewModel

import com.app.marvel.data.CharacterInfo

sealed class APITask{
    object Init: APITask()
    object Loading: APITask()
    sealed class Response: APITask(){
        data class error(val error:String): Response()
        data class Ok(val payload:List<CharacterInfo>): Response()
    }
}