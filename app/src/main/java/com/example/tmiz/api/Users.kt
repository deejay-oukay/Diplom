package com.example.tmiz.api

interface Users {
    /*
    * Регистрация пользователя
    * В случае успеха возвращает id только что созданного пользователя
    * */
    fun reg(login: String, password: String): String?

    /*
    * Авторизация пользователя
    * В случае успеха возвращает id только что созданной сессии
    * */
    fun auth(login: String, password: String): String?
}