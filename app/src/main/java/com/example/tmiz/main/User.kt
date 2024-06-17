package com.example.tmiz.main

import com.example.tmiz.api.Users

class User: Users {
    private var login: String? = null
    private var password: String? = null
    private var sessionId: String? = null
    override fun reg(login: String, password: String): String? {
        TODO("Not yet implemented")
    }

    override fun auth(login: String, password: String): String? {
        TODO("Not yet implemented")
    }
}