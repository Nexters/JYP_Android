package com.jyp.journeypiki.presentation

import com.jyp.core.model.UserInfoResponseModel


sealed class UiState {
    object Loading : UiState()
    data class Success(val searchPlaceResult: UserInfoResponseModel) : UiState()
    data class Failure(val message: String) : UiState()
}