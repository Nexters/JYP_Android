package com.jyp.core

import com.jyp.core.model.UserInfoResponseModel
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path


interface UserInfoApi {
    @GET("/users")
    suspend fun getUserInfo(
        @HeaderMap userInfoHeaders: Map<String, String>,
        @Path("id") id: String
    ): UserInfoResponseModel
}
