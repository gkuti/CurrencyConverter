package com.gamik.data.util


import com.gamik.domain.util.CallError
import com.gamik.domain.util.Result
import retrofit2.Response

suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): Result<T> {
    return try {
        call().run {
            if (isSuccessful) {
                return Result.Success(body())
            } else if (code() == 429) {
                Result.Failure(CallError.RATE_LIMIT_EXCEEDED)
            } else
                return Result.Failure(CallError.API_ERROR)
        }
    } catch (e: Exception) {
        Result.Failure(CallError.CONNECTION_ERROR)
    }
}