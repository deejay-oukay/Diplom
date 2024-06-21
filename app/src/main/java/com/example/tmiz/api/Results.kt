package com.example.tmiz.api

import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("code") val code: Int,
    @SerializedName("body") val body: Body?
)