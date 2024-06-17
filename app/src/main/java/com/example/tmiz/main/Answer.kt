package com.example.tmiz.main

import com.example.tmiz.api.Answers

class Answer: Answers {
    override fun send(
        sessionId: String,
        questionId: String,
        answers: Array<String>,
    ): Array<String> {
        TODO("Not yet implemented")
    }
}