package com.jyp.core.search_place.domain

import com.jyp.core.search_place.domain.model.SearchPlaceResponseModel
import retrofit2.http.GET
import retrofit2.http.Query


interface SearchPlaceApi {
    @GET("/v2/local/search/keyword.json")
    suspend fun getSearchPlaceResult(
        @Query("query") query: String
    ): SearchPlaceResponseModel
}
