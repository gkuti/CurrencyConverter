package com.gamik.data.util

import com.gamik.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

fun <T : Any> Flow<Result<T>>.applyCommonSideEffects() =
    onStart { emit(Result.Loading) }