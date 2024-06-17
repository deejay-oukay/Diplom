package com.example.tmiz.api

interface Questions {
    /*
    * Создаёт вопрос
    * В случае успеха возвращает id только что созданного вопроса
    * */
    fun create(sessionId: String, question: String, answers: Array<String>?)

    /*
    * Запрашивает массив случайных вопросов, на которые пользователь ещё не ответил
    * Результат нужно сохранить в БД
    * */
    fun random(sessionId: String)

    /*
    * Получает массив вопросов, созданных пользователем
    * Результат нужно сохранить в БД
    * */
    fun mine(sessionId: String)
}