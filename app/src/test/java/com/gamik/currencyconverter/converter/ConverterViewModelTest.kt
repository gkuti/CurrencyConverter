package com.gamik.currencyconverter.converter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gamik.currencyconverter.ui.converter.ConverterViewModel
import com.gamik.currencyconverter.util.TestCoroutineRule
import com.gamik.data.repository.ConverterRepositoryImpl
import com.gamik.domain.model.ConvertResponse
import com.gamik.domain.model.SymbolResponse
import com.gamik.domain.usecase.ConverterUsecase
import com.gamik.domain.util.CallError
import com.gamik.domain.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ConverterViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Test
    fun get_symbols_success() {
        val symbolResponse = SymbolResponse(true, mapOf(Pair("AED", "United Arab Emirates Dirham")))
        val converterRepository: ConverterRepositoryImpl = mock {
            on { getSymbols() } doReturn flow {
                emit(Result.Success(symbolResponse))
            }
        }

        val converterViewModel = ConverterViewModel(ConverterUsecase(converterRepository))
        converterViewModel.fetchSymbols()
        val result = converterViewModel.symbolViewState.value
        Assert.assertEquals(Result.Success(symbolResponse), result)
        Assert.assertEquals(true, (result as Result.Success).data?.symbols?.keys?.contains("AED"))
    }

    @Test
    fun get_symbols_failure() {
        val error = CallError.API_ERROR
        val converterRepository: ConverterRepositoryImpl = mock {
            on { getSymbols() } doReturn flow {
                emit(Result.Failure(error))
            }
        }

        val converterViewModel = ConverterViewModel(ConverterUsecase(converterRepository))
        converterViewModel.fetchSymbols()
        val result = converterViewModel.symbolViewState.value
        Assert.assertEquals(Result.Failure(error), result)
        Assert.assertNotEquals(CallError.RATE_LIMIT_EXCEEDED, (result as Result.Failure).error)
    }

    @Test
    fun get_rate_success() {
        val rate = 1.2
        val base = "USD"
        val target = "GBP"
        val amount = 1.0
        val convertResponse = ConvertResponse(result = rate)
        val converterRepository: ConverterRepositoryImpl = mock {
            on { convert(base, target, amount) } doReturn flow {
                emit(Result.Success(convertResponse))
            }
        }

        val converterViewModel = ConverterViewModel(ConverterUsecase(converterRepository))
        converterViewModel.convert(base, target, amount)
        val result = converterViewModel.rateViewState.value
        Assert.assertEquals(Result.Success(convertResponse), result)
        Assert.assertEquals(rate, (result as Result.Success).data?.result)
    }

    @Test
    fun get_rate_failure() {
        val error = CallError.RATE_LIMIT_EXCEEDED
        val base = "USD"
        val target = "GBP"
        val amount = 1.0
        val converterRepository: ConverterRepositoryImpl = mock {
            on { convert(base, target, amount) } doReturn flow {
                emit(Result.Failure(error))
            }
        }

        val converterViewModel = ConverterViewModel(ConverterUsecase(converterRepository))
        converterViewModel.convert(base, target, amount)
        val result = converterViewModel.rateViewState.value
        Assert.assertEquals(Result.Failure(error), result)
        Assert.assertNotEquals(CallError.CONNECTION_ERROR, (result as Result.Failure).error)
    }
}