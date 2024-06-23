package com.example.tmiz.api

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

interface Api {
    @FormUrlEncoded
    @POST("/api/questions-create/")
    suspend fun questionCreate(
        @Field("question") question: String,
        @Field("answers") answers: String,
        @Field("multi") multi: Boolean
    ): Response<Results>
}