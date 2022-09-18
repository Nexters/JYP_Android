package com.jyp.core

sealed class ApiResponse {
    object Loading : ApiResponse()
    data class Success<out T>(val data: T) : ApiResponse()
    data class Failure(val message: String) : ApiResponse()
}