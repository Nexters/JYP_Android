package com.jyp.feature_my_journey.domain

import com.jyp.core_network.jyp.ApiResult
import com.jyp.core_network.jyp.apiUnitResult
import javax.inject.Inject

class LeaveJourneyUseCase @Inject constructor(
    private val journeyRepository: JourneyRepository,
) {
    suspend operator fun invoke(
        journeyId: String
    ): ApiResult<Any> {
        return apiUnitResult {
            journeyRepository.leaveJourney(journeyId)
        }
    }
}
