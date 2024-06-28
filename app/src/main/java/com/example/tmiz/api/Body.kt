package com.example.tmiz.api

import com.google.gson.annotations.SerializedName

data class Body(
    @SerializedName("questionId") val questionId: Int,
    @SerializedName("question") val question: String?,
    @SerializedName("answers") val answers: Array<String>?,
//    @SerializedName("answers") val answersIds: Array<String>?,
    @SerializedName("multi") val multi: Boolean?
)
