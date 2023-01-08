package com.jyp.feature_planner.domain

import com.jyp.core_network.jyp.model.Tag
import com.jyp.core_network.jyp.model.enumerate.TagOrientation
import com.jyp.jyp_design.ui.tag.TagType

class PlannerTagMapper {
    fun toPlannerTag(tag: Tag): PlannerTag {
        return PlannerTag(
            type = when (tag.orientation) {
                TagOrientation.LIKE -> TagType.Like
                TagOrientation.DISLIKE -> TagType.Dislike
                TagOrientation.NO_MATTER -> TagType.Soso
            },
            content = tag.topic,
        )
    }

    fun toTag(plannerTag: PlannerTag): Tag {
        return Tag(
            plannerTag.content,
            when (plannerTag.type) {
                TagType.Dislike -> TagOrientation.DISLIKE
                TagType.Like -> TagOrientation.LIKE
                TagType.Soso -> TagOrientation.NO_MATTER
            },
        )
    }
}
