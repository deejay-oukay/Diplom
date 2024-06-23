package com.example.tmiz.api

import com.google.gson.annotations.SerializedName

data class Body(
    @SerializedName("questionId") val questionId: Int
)
