package com.example.tmiz.states

sealed class StateMine {
    object Success : StateMine()
    data class Error(val error: String) : StateMine()
    object Loading : StateMine()
}

