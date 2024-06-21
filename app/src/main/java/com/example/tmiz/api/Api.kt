package com.example.tmiz.api

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Body

interface Api {
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @POST("/api/questions-create/")
    suspend fun questionCreate(@Body requestBody: RequestBody): Response<Results>
}