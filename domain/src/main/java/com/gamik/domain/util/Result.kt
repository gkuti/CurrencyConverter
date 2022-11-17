package com.gamik.domain.util

sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T?) : Result<T>()
    data class Failure(val error: CallError) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

enum class CallError(val message: String) {
    RATE_LIMIT_EXCEEDED("You have exceeded your rate limit, please try again later"),
    API_ERROR("Unable to complete request, please try again"),
    CONNECTION_ERROR("Unable to connect, please check your connection and try again")
}


