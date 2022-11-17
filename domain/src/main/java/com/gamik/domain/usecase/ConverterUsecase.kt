package com.gamik.domain.usecase

import com.gamik.domain.model.ConvertResponse
import com.gamik.domain.model.SymbolResponse
import com.gamik.domain.repository.ConverterRepository
import com.gamik.domain.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConverterUsecase @Inject constructor(private val converterRepository: ConverterRepository) {
    fun getSymbols(): Flow<Result<SymbolResponse>> =
        converterRepository.getSymbols()

    fun convert(from: String, to: String, amount: Double): Flow<Result<ConvertResponse>> =
        converterRepository.convert(from, to, amount)
}