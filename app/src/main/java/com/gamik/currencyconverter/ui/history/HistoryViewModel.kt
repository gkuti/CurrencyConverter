package com.gamik.currencyconverter.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamik.domain.model.HistoryResponse
import com.gamik.domain.usecase.HistoryUsecase
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
        MutableStateFlow<HistoryViewState>(
            HistoryViewState.Success(HistoryResponse(rates = emptyMap()))
        )
    val historyViewState: StateFlow<HistoryViewState> = _historyViewState

    fun getHistory(startDate: String, endDate: String, base: String, symbol: String) {
        _historyViewState.value = HistoryViewState.Loading

        viewModelScope.launch {
            runCatching {
                usecase.getHistory(startDate, endDate, base, symbol)
            }.onFailure {
                _historyViewState.value = HistoryViewState.Error
            }.onSuccess {
                _historyViewState.value = HistoryViewState.Success(it)
            }
        }
    }
}

sealed class HistoryViewState {
    object Loading : HistoryViewState()
    object Error : HistoryViewState()
    data class Success(val response: HistoryResponse) : HistoryViewState()
}