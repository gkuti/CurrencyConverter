package com.gamik.domain.repository

import com.gamik.domain.model.HistoryResponse

interface HistoryRepository {
    suspend fun getHistory(
        startDate: String,
        endDate: String,
        base: String,
        symbol: String
    ): HistoryResponse
}