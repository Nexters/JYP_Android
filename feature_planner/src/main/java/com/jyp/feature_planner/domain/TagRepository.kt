package com.jyp.feature_planner.domain

import com.jyp.core_network.jyp.model.response.TagsResponse

interface TagRepository {
    suspend fun getDefaultTags(): TagsResponse
    suspend fun getTags(plannerId: String): TagsResponse
}