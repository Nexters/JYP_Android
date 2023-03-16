package com.jyp.core_network.util

import com.google.gson.Gson
import com.jyp.core_network.jyp.JypApiFailureResponse
import retrofit2.HttpException


fun Throwable.toJypApiFailure(): JypApiFailureResponse? {
    return when (this is HttpException) {
        true -> this.response()?.errorBody()?.charStream()?.let {
            Gson().fromJson(it, JypApiFailureResponse::class.java)
        }
        false -> null
    }
}