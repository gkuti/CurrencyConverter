package com.gamik.currencyconverter.ui.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamik.domain.model.ConvertResponse
import com.gamik.domain.model.SymbolResponse
import com.gamik.domain.usecase.ConverterUsecase
import com.gamik.domain.util.Result
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
        MutableStateFlow<Result<SymbolResponse>>(Result.Success(null))
    val symbolViewState: StateFlow<Result<SymbolResponse>> = _symbolViewState

    private val _rateViewState =
        MutableStateFlow<Result<ConvertResponse>>(Result.Success(null))
    val rateViewState: StateFlow<Result<ConvertResponse>> = _rateViewState

    init {
        fetchSymbols()
    }

    fun fetchSymbols() {
        viewModelScope.launch {
            usecase.getSymbols().collect {
                _symbolViewState.value = it
            }
        }
    }

    fun convert(from: String, to: String, amount: Double) {
        viewModelScope.launch {
            usecase.convert(from, to, amount).collect {
                _rateViewState.value = it
            }
        }
    }
}