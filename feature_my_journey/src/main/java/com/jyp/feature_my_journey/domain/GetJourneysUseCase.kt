package com.jyp.feature_my_journey.domain

import javax.inject.Inject

class GetJourneysUseCase @Inject constructor(
        private val journeyRepository: JourneyRepository,
) {
    suspend operator fun invoke(): Any {
        return journeyRepository.getJourneys()
    }
}
