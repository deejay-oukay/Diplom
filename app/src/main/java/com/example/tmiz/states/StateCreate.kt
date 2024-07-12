package com.example.tmiz.states

sealed class StateCreate {
    object Default : StateCreate()
    object Success : StateCreate()
    data class Error(val error: String) : StateCreate()
    object Loading : StateCreate()
}

