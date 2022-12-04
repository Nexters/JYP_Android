package com.jyp.feature_planner.data

import com.jyp.core_network.jyp.JypApi
import com.jyp.core_network.jyp.model.enumerate.TagOrientation
import com.jyp.core_network.jyp.model.request.CreatePlannerRequestBody
import com.jyp.feature_planner.domain.Tag
import com.jyp.jyp_design.ui.tag.TagType
import javax.inject.Inject

class CreatePlannerDataSource @Inject constructor(
        private val jypApi: JypApi,
) {
    suspend fun createPlanner(
            title: String,
            startSec: Long,
            endSec: Long,
            themePath: String,
            tags: List<Tag>
    ): Any {
        val body = CreatePlannerRequestBody(
                title,
                startSec,
                endSec,
                themePath,
                tags.map { tag ->
                    com.jyp.core_network.jyp.model.Tag(
                            tag.content,
                            when (tag.type) {
                                is TagType.Dislike -> TagOrientation.DISLIKE
                                is TagType.Like -> TagOrientation.LIKE
                                is TagType.Soso -> TagOrientation.NO_MATTER
                            },
                    )
                }
        )

        return jypApi.createPlanner(body)
    }
}
