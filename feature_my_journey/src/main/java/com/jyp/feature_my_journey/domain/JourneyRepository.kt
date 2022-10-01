package com.jyp.feature_my_journey.domain

interface JourneyRepository {
    suspend fun getJourneys(): Any
}
