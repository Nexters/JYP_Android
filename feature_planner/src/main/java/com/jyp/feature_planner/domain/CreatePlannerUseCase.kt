package com.jyp.feature_planner.domain

import javax.inject.Inject

class CreatePlannerUseCase @Inject constructor(
        private val createPlannerRepository: CreatePlannerRepository,
) {
    suspend operator fun invoke(
            title: String,
            startSec: Long,
            endSec: Long,
            themePath: String,
            tags: List<Tag>,
    ): Any {
        return createPlannerRepository.createPlanner(title, startSec, endSec, themePath, tags)
    }
}
