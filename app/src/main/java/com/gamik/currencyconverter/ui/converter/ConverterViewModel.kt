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
}

sealed class SymbolViewState {
    object Loading : SymbolViewState()
    object Error : SymbolViewState()
    data class Success(val response: SymbolResponse) : SymbolViewState()
}