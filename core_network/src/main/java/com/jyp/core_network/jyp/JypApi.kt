package com.jyp.core_network.jyp

import com.jyp.core_network.jyp.request.CreateUserRequestBody
import com.jyp.core_network.jyp.response.CreateUserResponse
import retrofit2.http.*

interface JypApi {
    @POST("users")
    suspend fun createUser(@Body body: CreateUserRequestBody): CreateUserResponse

}
