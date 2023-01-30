package com.jyp.feature_planner.domain

import com.jyp.core_network.jyp.model.Pikme

class PlannerPikmeMapper {
    fun toPlannerPikme(pikme: Pikme): PlannerPikme {
        return PlannerPikme(
            title = pikme.name,
            address = pikme.address,
            category = pikme.category.name,
            likeCount = 0,
        )
    }
}
