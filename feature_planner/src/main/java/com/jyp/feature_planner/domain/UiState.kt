package com.jyp.feature_planner.domain


sealed class UiState {
    object Loading : UiState()
    data class Success(
        val journeys: Any
        ) : UiState()

    data class Failure(
        val throwable: Throwable
    ) : UiState()
}