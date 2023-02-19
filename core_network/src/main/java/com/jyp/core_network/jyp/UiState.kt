package com.jyp.core_network.jyp


sealed class UiState<out T : Any> {
    object Loading : UiState<Nothing>()
    data class Success<out T : Any>(val data: T) : UiState<T>()
    data class Failure(val throwable: Throwable) : UiState<Throwable>()
}
