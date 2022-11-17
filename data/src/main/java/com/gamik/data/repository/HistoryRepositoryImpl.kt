package com.gamik.data.repository

import com.gamik.data.remote.CurrencyService
import com.gamik.data.util.apiCall
import com.gamik.data.util.applyCommonSideEffects
import com.gamik.domain.model.HistoryResponse
import com.gamik.domain.repository.HistoryRepository
import com.gamik.domain.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val currencyService: CurrencyService
) : HistoryRepository {
    override suspend fun getHistory(
        startDate: String,
        endDate: String,
        base: String,
        symbol: String
    ): Flow<Result<HistoryResponse>> = flow {
        val result = withContext(Dispatchers.IO) {
            apiCall {
                currencyService.history(startDate, endDate, base, symbol)
            }
        }
        emit(result)
    }.applyCommonSideEffects()
}