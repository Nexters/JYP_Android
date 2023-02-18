package com.jyp.feature_planner.domain

import com.jyp.core_network.jyp.model.Piki
import com.jyp.core_network.jyp.model.enumerate.getDrawableResourceId
import com.jyp.core_network.jyp.model.enumerate.toName
import com.jyp.core_network.jyp.model.enumerate.toPlaceCategory

class PlannerPikiMapper {
    fun toPlannerPiki(piki: Piki): PlannerPiki {
        return PlannerPiki(
            id = piki.id,
            name = piki.name,
            address = piki.address,
            category = piki.category.toName(),
            categoryIconRes = piki.category.getDrawableResourceId(),
            longitude = piki.longitude,
            latitude = piki.latitude,
            link = piki.link,
        )
    }

    fun toPiki(plannerPiki: PlannerPiki): Piki {
        return Piki(
            id = plannerPiki.id,
            name = plannerPiki.name,
            address = plannerPiki.address,
            category = plannerPiki.category.toPlaceCategory(),
            longitude = plannerPiki.longitude,
            latitude = plannerPiki.latitude,
            link = plannerPiki.link,
        )
    }
}
