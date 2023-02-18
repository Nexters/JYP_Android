package com.jyp.core_network.jyp

import com.jyp.core_network.jyp.model.request.*
import com.jyp.core_network.jyp.model.response.*
import retrofit2.http.*

interface JypApi {
    @GET("auth/kakao/login")
    suspend fun signInWithKakao(
    ): KakaoSignInResponse

    @POST("users")
    suspend fun createUserAccount(
        @Body body: CreateUserRequestBody
    ): UserResponse

    @GET("users/{id}")
    suspend fun getUser(
        @Path("id") userId: String
    ): UserResponse

    @GET("users/me")
    suspend fun getMe(): UserResponse

    @GET("journeys")
    suspend fun getJourneys(): JourneysResponse

    @POST("journeys")
    suspend fun createPlanner(@Body body: CreatePlannerRequestBody): Any

    @POST("journeys/{id}/join")
    suspend fun joinPlanner(
        @Path("id") plannerId: String,
        @Body joinPlannerRequestBody: JoinPlannerRequestBody
    ): JypBaseResponse<Any>

    @GET("journeys/{id}")
    suspend fun getPlanner(@Path("id") id: String): PlannerResponse

    @POST("journeys/{id}/pikmis")
    suspend fun addPikme(
        @Path("id") plannerId: String,
        @Body addPlaceRequestBody: AddPlaceRequestBody,
    ): JypBaseResponse<Any>

    @POST("journeys/{id}/pikis")
    suspend fun setPikis(
        @Path("id") journeyId: String,
        @Body setPikiRequestBody: SetPikiRequestBody,
    ): JypBaseResponse<Any>
}
