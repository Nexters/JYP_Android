package com.jyp.feature_planner.presentation.create_planner.model

import com.jyp.feature_planner.domain.PlannerTag

sealed interface CreatePlannerSubmit {
    data class Title(val title: String) : CreatePlannerSubmit
    data class Date(val from: Long, val to: Long) : CreatePlannerSubmit
    data class Taste(val tags: List<PlannerTag>) : CreatePlannerSubmit
}
