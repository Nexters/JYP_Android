package com.jyp.feature_add_place.presentation

import com.jyp.feature_add_place.data.model.SearchPlaceResultModel


sealed class ApiState {
    object Loading : ApiState()
    data class Success(val searchPlaceResult: List<SearchPlaceResultModel>) : ApiState()
    data class Failure(val message: String) : ApiState()
}