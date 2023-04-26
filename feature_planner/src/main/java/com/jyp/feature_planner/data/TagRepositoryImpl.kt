package com.jyp.feature_planner.data

import com.jyp.core_network.jyp.model.response.TagsResponse
import com.jyp.feature_planner.domain.TagRepository
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(
    private val tagDataSource: TagDataSource,
) : TagRepository {
    override suspend fun getDefaultTags(): TagsResponse {
        return tagDataSource.getDefaultTags()
    }

    override suspend fun getTags(plannerId: String): TagsResponse {
        return tagDataSource.getTags(plannerId)
    }
}