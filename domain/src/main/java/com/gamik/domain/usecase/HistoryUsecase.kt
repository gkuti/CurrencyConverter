package com.gamik.domain.usecase

import com.gamik.domain.model.HistoryResponse
import com.gamik.domain.repository.HistoryRepository
import javax.inject.Inject

class HistoryUsecase @Inject constructor(private val historyRepository: HistoryRepository) {
    suspend fun getHistory(
        startDate: String, endDate: String, base: String, symbol: String
    ): HistoryResponse = historyRepository.getHistory(startDate, endDate, base, symbol)
}