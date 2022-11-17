package com.gamik.data.repository

import com.gamik.data.remote.CurrencyService
import com.gamik.data.util.apiCall
import com.gamik.data.util.applyCommonSideEffects
import com.gamik.domain.model.ConvertResponse
import com.gamik.domain.model.SymbolResponse
import com.gamik.domain.repository.ConverterRepository
import com.gamik.domain.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConverterRepositoryImpl @Inject constructor(
    private val currencyService: CurrencyService
) : ConverterRepository {
    override suspend fun getSymbols(): Flow<Result<SymbolResponse>> = flow {
        val result = withContext(Dispatchers.IO) {
            apiCall {
                currencyService.getSymbols()
            }
        }
        emit(result)
    }.applyCommonSideEffects()

    override suspend fun convert(
        from: String,
        to: String,
        amount: Double
    ): Flow<Result<ConvertResponse>> = flow {
        val result = withContext(Dispatchers.IO) {
            apiCall {
                currencyService.convert(from, to, amount)
            }
        }
        emit(result)
    }.applyCommonSideEffects()
}