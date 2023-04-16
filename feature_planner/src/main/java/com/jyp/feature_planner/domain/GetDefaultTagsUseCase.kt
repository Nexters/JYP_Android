package com.jyp.feature_planner.domain

import com.jyp.core_network.jyp.ApiResult
import com.jyp.core_network.jyp.apiResult
import com.jyp.core_network.jyp.model.Tags
import javax.inject.Inject

class GetDefaultTagsUseCase @Inject constructor(
    private val tagRepository: TagRepository,
) {
    suspend operator fun invoke(): ApiResult<Tags> {
        return apiResult {
            tagRepository.getDefaultTags()
        }
    }
}