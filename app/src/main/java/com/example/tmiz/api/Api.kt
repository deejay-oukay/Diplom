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
    @POST("/api/questions-random/")
    suspend fun questionsRandom(): Response<Results>
    @FormUrlEncoded
    @POST("/api/answers-send/")
    suspend fun answersSend(
        @Field("questionId") questionId: String,
        @Field("answers") answers: String?
    ): Response<Results>
    @POST("/api/questions-mine/")
    suspend fun questionsMine(): Response<Results>
}