package com.gamik.data.repository

import com.gamik.data.remote.CurrencyService
import com.gamik.domain.model.HistoryResponse
import com.gamik.domain.repository.HistoryRepository
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val currencyService: CurrencyService
) : HistoryRepository {
    override suspend fun getHistory(
        startDate: String,
        endDate: String,
        base: String,
        symbol: String
    ): HistoryResponse {
        return currencyService.history(startDate, endDate, base, symbol)
    }
}