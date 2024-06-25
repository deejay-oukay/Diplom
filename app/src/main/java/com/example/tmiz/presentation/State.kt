package com.example.tmiz.presentation

sealed class State {
    object Default : State()
    object Success : State()
    data class Error(val error: String) : State()
    object Loading : State()
}

