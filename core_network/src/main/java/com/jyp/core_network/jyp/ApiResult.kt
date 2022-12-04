package com.jyp.core_network.jyp


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

fun<T> ApiResult<T>.onFailure(action: (Throwable) -> Unit): ApiResult<T> {
    if (this is ApiResult.Failure) {
        action.invoke(this.throwable)
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
