package com.jyp.feature_planner.data

import com.jyp.feature_planner.domain.CreatePlannerRepository
import com.jyp.feature_planner.domain.Tag
import javax.inject.Inject

class CreatePlannerRepositoryImpl @Inject constructor(
        private val createPlannerDataSource: CreatePlannerDataSource,
) : CreatePlannerRepository {
    override suspend fun createPlanner(
            title: String,
            startSec: Long,
            endSec: Long,
            themePath: String,
            tags: List<Tag>,
    ): Any {
        return createPlannerDataSource.createPlanner(title, startSec, endSec, themePath, tags)
    }
}
