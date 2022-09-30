package com.jyp.journeypiki.util

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException


inline fun <R> resultOf(block: () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (t: TimeoutCancellationException) {
        Result.failure(t)
    } catch (c: CancellationException) {
        throw c
    } catch (e: Exception) {
        Result.failure(e)
    }
}