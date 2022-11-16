package com.gamik.domain.repository

import com.gamik.domain.model.ConvertResponse
import com.gamik.domain.model.SymbolResponse

interface ConverterRepository {
    suspend fun getSymbols(): SymbolResponse
    suspend fun convert(from: String, to: String, amount: Double): ConvertResponse
}