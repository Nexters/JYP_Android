package com.jyp.core_network.jyp

import com.google.gson.Gson
import retrofit2.HttpException


sealed interface ApiResult<T> {
    data class Success<T>(val data: T) : ApiResult<T>
    data class Failure(val throwable: Throwable) : ApiResult<Nothing>
}

fun<T> ApiResult<T>.onSuccess(action: (T) -> Unit): ApiResult<T> {
    if (this is ApiResult.Success) {
        action.invoke(this.data)
    }

    return this
}

fun<Nothing> ApiResult<Nothing>.onFailure(action: (JypFailureResponse) -> Unit): ApiResult<Nothing> {
    if (this is ApiResult.Failure) {
        val error = if (this.throwable is HttpException) {
            val response = this.throwable.response()?.errorBody()?.charStream()
            when (response != null) {
                true -> Gson().fromJson(response, JypFailureResponse::class.java)
                false -> JypFailureResponse(
                    code = this.throwable.stackTraceToString(),
                    message = this.throwable.localizedMessage ?: ""
                )
            }

        } else {
            JypFailureResponse(
                code = this.throwable.stackTraceToString(),
                message = this.throwable.localizedMessage ?: ""
            )
        }
        action.invoke(error)
    }
    return this
}

@Suppress("UNCHECKED_CAST")
inline fun <T : Any> apiResult(call: () -> JypBaseResponse<T>): ApiResult<T> {
    return runCatching {
        ApiResult.Success(call.invoke().data)
    }.getOrElse {
        ApiResult.Failure(it)
    } as ApiResult<T>
}
