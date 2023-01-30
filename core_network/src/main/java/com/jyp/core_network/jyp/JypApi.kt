package com.jyp.core_network.jyp

import com.jyp.core_network.jyp.model.request.AddPlaceRequestBody
import com.jyp.core_network.jyp.model.request.CreatePlannerRequestBody
import com.jyp.core_network.jyp.model.request.CreateUserRequestBody
import com.jyp.core_network.jyp.model.response.JourneysResponse
import com.jyp.core_network.jyp.model.response.PlannerResponse
import com.jyp.core_network.jyp.model.response.UserResponse
import retrofit2.http.*

interface JypApi {
    @POST("users")
    suspend fun createUser(@Body body: CreateUserRequestBody): UserResponse

    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: String): UserResponse

    @GET("journeys")
    suspend fun getJourneys(): JourneysResponse

    @POST("journeys")
    suspend fun createPlanner(@Body body: CreatePlannerRequestBody): Any

    @GET("journeys/{id}")
    suspend fun getPlanner(@Path("id") id: String): PlannerResponse

    @POST("journeys/{id}/pikmis")
    suspend fun addPikme(
        @Path("id") plannerId: String,
        @Body addPlaceRequestBody: AddPlaceRequestBody,
    ): JypBaseResponse<Any>
}
