package com.jyp.feature_planner.domain

import com.jyp.core_network.jyp.JypWithoutDataResponse
import com.jyp.core_network.jyp.model.request.JoinPlannerRequestBody
import com.jyp.core_network.jyp.model.response.TagsResponse

interface TagRepository {
    suspend fun getDefaultTags(): TagsResponse

    suspend fun getTags(
        plannerId: String,
        isIncludeDefaultTags: Boolean
    ): TagsResponse

    suspend fun editTags(
        plannerId: String,
        tags: JoinPlannerRequestBody
    ): JypWithoutDataResponse
}