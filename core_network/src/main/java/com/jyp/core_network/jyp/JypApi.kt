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

    @DELETE("users/{id}")
    suspend fun withdrawAccount(
        @Path("id") userId: String
    ): WithdrawalResponse

    @GET("users/{id}")
    suspend fun getUser(
        @Path("id") userId: String
    ): UserResponse

    @GET("users/me")
    suspend fun getMe(): UserResponse

    @PATCH("users/{id}")
    suspend fun updateUserProfile(
        @Path("id") id: String,
        @Body setProfileRequestBody: SetProfileRequestBody,
    ): UserResponse

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

    @POST("/journeys/{id}/drop")
    suspend fun leaveJourney(
        @Path("id") id: String
    ): JypBaseResponse<Any>

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

    @POST("journeys/{journeyId}/pikmis/{pikmeId}/likes")
    suspend fun likePikme(
        @Path("journeyId") journeyId: String,
        @Path("pikmeId") pikMeId: String,
    ): JypBaseResponse<Any>

    @POST("journeys/{journeyId}/pikmis/{pikmeId}/undoLikes")
    suspend fun undoLikePikme(
        @Path("journeyId") journeyId: String,
        @Path("pikmeId") pikMeId: String,
    ): JypBaseResponse<Any>
}
