package com.jyp.core_network.jyp

import com.jyp.core_network.jyp.request.CreateUserRequestBody
import com.jyp.core_network.jyp.response.UserResponse
import retrofit2.http.*

interface JypApi {
    @POST("users")
    suspend fun createUser(@Body body: CreateUserRequestBody): UserResponse

    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: String): UserResponse

    @GET("journeys")
    suspend fun getJourneys(): Any
}
