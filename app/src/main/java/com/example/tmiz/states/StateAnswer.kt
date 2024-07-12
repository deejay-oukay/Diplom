package com.example.tmiz.states

sealed class StateAnswer {
   object SuccessGet : StateAnswer()
   object SuccessSend : StateAnswer()
    data class ErrorGet(val error: String) : StateAnswer()
    data class ErrorSend(val error: String) : StateAnswer()
    object Loading : StateAnswer()
    object Sending : StateAnswer()
}

