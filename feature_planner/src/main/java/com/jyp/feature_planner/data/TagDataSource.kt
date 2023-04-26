package com.jyp.feature_planner.data

import com.jyp.core_network.jyp.JypApi
import com.jyp.core_network.jyp.model.response.TagsResponse
import javax.inject.Inject

class TagDataSource @Inject constructor(
    private val jypApi: JypApi,
) {
    suspend fun getDefaultTags(): TagsResponse {
        return jypApi.getDefaultTags()
    }

    suspend fun getTags(plannerId: String): TagsResponse {
        return jypApi.getTags(plannerId)
    }
}