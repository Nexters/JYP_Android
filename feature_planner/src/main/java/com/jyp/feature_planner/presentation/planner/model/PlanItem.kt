package com.jyp.feature_planner.presentation.planner.model

import com.jyp.feature_planner.domain.PlannerPiki

data class PlanItem(
        val day: Int,
        val pikis: List<PlannerPiki>,
)
