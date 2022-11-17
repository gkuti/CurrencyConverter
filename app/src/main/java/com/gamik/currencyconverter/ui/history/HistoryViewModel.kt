package com.gamik.currencyconverter.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamik.domain.model.HistoryResponse
import com.gamik.domain.usecase.HistoryUsecase
import com.gamik.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val usecase: HistoryUsecase
) : ViewModel() {
    private val _historyViewState =
        MutableStateFlow<Result<HistoryResponse>>(
            Result.Success(HistoryResponse(rates = emptyMap()))
        )
    val historyViewState: StateFlow<Result<HistoryResponse>> = _historyViewState

    fun getHistory(startDate: String, endDate: String, base: String, symbol: String) {

        viewModelScope.launch {
            usecase.getHistory(startDate, endDate, base, symbol)
                .collect {
                    _historyViewState.value = it
                }
        }
    }
}