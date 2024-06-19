package com.example.tmiz.ui.viewmodel

sealed class State {
    data object Loading: State()
    data object Finish: State()
    data object Error: State()
}