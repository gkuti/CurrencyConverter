package com.gamik.domain.usecase

import com.gamik.domain.model.SymbolResponse
import com.gamik.domain.repository.ConverterRepository
import javax.inject.Inject

class ConverterUsecase @Inject constructor(private val converterRepository: ConverterRepository) {
    suspend fun getSymbols(): SymbolResponse =
        converterRepository.getSymbols()
}