package com.example.tmiz.api

import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST

interface Api {
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @POST("/api/questions-create/")
    suspend fun questionCreate(): Response<String>
}