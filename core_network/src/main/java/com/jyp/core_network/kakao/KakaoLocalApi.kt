package com.jyp.core_network.kakao

import com.jyp.core.BuildConfig
import com.jyp.core_network.kakao.response.KakaoLocalResponseModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface KakaoLocalApi {

    @GET("/v2/local/search/keyword.json")
    @Headers("Authorization: KakaoAK ${BuildConfig.KAKAO_REST_API_KEY}")
    suspend fun getKakaoLocalSearchResult(
        @Query("query") query: String
    ): KakaoLocalResponseModel
}
