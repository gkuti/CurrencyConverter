package com.gamik.domain.repository

import com.gamik.domain.model.ConvertResponse
import com.gamik.domain.model.SymbolResponse
import com.gamik.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface ConverterRepository {
    suspend fun getSymbols(): Flow<Result<SymbolResponse>>
    suspend fun convert(from: String, to: String, amount: Double): Flow<Result<ConvertResponse>>
}