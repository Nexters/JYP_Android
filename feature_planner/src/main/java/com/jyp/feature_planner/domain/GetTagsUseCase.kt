package com.jyp.feature_planner.domain

import com.jyp.core_network.jyp.ApiResult
import com.jyp.core_network.jyp.apiResult
import com.jyp.core_network.jyp.model.Tags
import javax.inject.Inject

class GetTagsUseCase @Inject constructor(
    private val tagRepository: TagRepository,
) {
    suspend operator fun invoke(
        plannerId: String,
        isIncludeDefaultTags: Boolean
    ): ApiResult<Tags> {
        return apiResult {
            tagRepository.getTags(plannerId, isIncludeDefaultTags)
        }
    }
}