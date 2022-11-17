package com.gamik.domain.repository

import com.gamik.domain.model.HistoryResponse
import com.gamik.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getHistory(
        startDate: String,
        endDate: String,
        base: String,
        symbol: String
    ): Flow<Result<HistoryResponse>>
}