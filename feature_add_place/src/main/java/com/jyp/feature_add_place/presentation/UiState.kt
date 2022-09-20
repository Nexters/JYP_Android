package com.jyp.feature_add_place.presentation

import com.jyp.feature_add_place.data.model.SearchPlaceResultModel


sealed class UiState {
    object Loading : UiState()
    data class Success(val searchPlaceResult: List<SearchPlaceResultModel>) : UiState()
    data class Failure(val message: String) : UiState()
}