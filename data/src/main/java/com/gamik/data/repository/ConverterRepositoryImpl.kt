package com.gamik.data.repository

import com.gamik.data.remote.CurrencyService
import com.gamik.domain.model.ConvertResponse
import com.gamik.domain.model.SymbolResponse
import com.gamik.domain.repository.ConverterRepository
import javax.inject.Inject

class ConverterRepositoryImpl @Inject constructor(
    private val currencyService: CurrencyService
) : ConverterRepository {
    override suspend fun getSymbols(): SymbolResponse {
        return currencyService.getSymbols()
    }

    override suspend fun convert(from: String, to: String, amount: Double): ConvertResponse {
        return currencyService.convert(from, to, amount)
    }
}