package com.jyp.feature_sign_in.util


sealed class UiState {
    object Loading : UiState()
    data class Success(
        val result: Any
    ) : UiState()
    data class Failure(
        val message: String
    ) : UiState()
}