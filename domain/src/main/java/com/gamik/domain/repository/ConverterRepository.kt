package com.gamik.domain.repository

import com.gamik.domain.model.SymbolResponse

interface ConverterRepository {
    suspend fun getSymbols(): SymbolResponse
}