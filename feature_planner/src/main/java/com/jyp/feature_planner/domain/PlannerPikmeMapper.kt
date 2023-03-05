package com.jyp.feature_planner.domain

import com.jyp.core_network.jyp.model.Pikme
import com.jyp.core_network.jyp.model.enumerate.toName

class PlannerPikmeMapper {
    fun toPlannerPikme(pikme: Pikme, myId: String): PlannerPikme {
        return PlannerPikme(
            id = pikme.id,
            title = pikme.name,
            address = pikme.address,
            category = pikme.category.toName(),
            likeCount = pikme.likeBy.size,
            liked = pikme.likeBy.any { it.id == myId },
            longitude = pikme.longitude,
            latitude = pikme.latitude,
            link = pikme.link,
        )
    }
}
