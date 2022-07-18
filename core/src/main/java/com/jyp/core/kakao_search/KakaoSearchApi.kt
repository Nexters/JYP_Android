package com.jyp.core.kakao_search

import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoSearchApi {
    @GET("/v2/local/search/keyword.json")
    suspend fun getPlace(
            @Query("query")query: String,
            @Query("y") y: String = "35.163177",
            @Query("x") x: String = "129.163634",
    ): Any
}
