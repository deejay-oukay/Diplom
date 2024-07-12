package com.example.tmiz.api

import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("questionId") val questionId: String,
    @SerializedName("question") val question: String
)
