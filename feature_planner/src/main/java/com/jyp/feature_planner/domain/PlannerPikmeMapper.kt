package com.jyp.feature_planner.domain

import com.jyp.core_network.jyp.model.Pikme
import com.jyp.core_network.jyp.model.enumerate.toName

class PlannerPikmeMapper {
    fun toPlannerPikme(pikme: Pikme): PlannerPikme {
        return PlannerPikme(
            title = pikme.name,
            address = pikme.address,
            category = pikme.category.toName(),
            likeCount = 0,
            longitude = pikme.longitude,
            latitude = pikme.latitude,
            link = pikme.link,
        )
    }
}
