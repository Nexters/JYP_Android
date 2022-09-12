package com.jyp.feature_planner.presentation.planner

import com.jyp.feature_planner.domain.Pikis

data class PlanItem(
        val day: Int,
        val pikis: List<Pikis>,
        val isCollapsed: Boolean,
)
