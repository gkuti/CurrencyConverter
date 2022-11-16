package com.gamik.currencyconverter.ui.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamik.domain.model.SymbolResponse
import com.gamik.domain.usecase.ConverterUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val usecase: ConverterUsecase
) : ViewModel() {
    private val _symbolViewState =
        MutableStateFlow<SymbolViewState>(SymbolViewState.Success(SymbolResponse(true, emptyMap())))
    val symbolViewState: StateFlow<SymbolViewState> = _symbolViewState

    private val _rateViewState =
        MutableStateFlow<RatesViewState>(RatesViewState.Success(-0.0))
    val rateViewState: StateFlow<RatesViewState> = _rateViewState

    init {
        fetchSymbols()
    }

    fun fetchSymbols() {
        _symbolViewState.value = SymbolViewState.Loading

        viewModelScope.launch {
            runCatching {
                usecase.getSymbols()
            }.onFailure {
                _symbolViewState.value = SymbolViewState.Error
            }.onSuccess { symbol ->
                _symbolViewState.value = SymbolViewState.Success(symbol)
            }
        }
    }

    fun convert(from: String, to: String, amount: Double) {
        _rateViewState.value = RatesViewState.Loading

        viewModelScope.launch {
            runCatching {
                usecase.convert(from, to, amount)
            }.onFailure {
                _rateViewState.value = RatesViewState.Error
            }.onSuccess {
                _rateViewState.value = RatesViewState.Success(it.result)
            }
        }
    }
}

sealed class SymbolViewState {
    object Loading : SymbolViewState()
    object Error : SymbolViewState()
    data class Success(val response: SymbolResponse) : SymbolViewState()
}

sealed class RatesViewState {
    object Loading : RatesViewState()
    object Error : RatesViewState()
    data class Success(val rate: Double) : RatesViewState()
}