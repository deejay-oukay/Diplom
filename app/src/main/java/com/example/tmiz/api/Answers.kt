package com.example.tmiz.api

interface Answers {
    /*
    * Отправляет ответ на вопрос
    * */
    fun send(sessionId: String, questionId: String, answers: Array<String>): Array<String>
}