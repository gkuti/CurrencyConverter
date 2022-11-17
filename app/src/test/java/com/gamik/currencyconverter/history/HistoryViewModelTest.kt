package com.gamik.currencyconverter.history

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gamik.currencyconverter.ui.history.HistoryViewModel
import com.gamik.currencyconverter.util.DateUtil
import com.gamik.currencyconverter.util.TestCoroutineRule
import com.gamik.data.repository.HistoryRepositoryImpl
import com.gamik.domain.model.HistoryResponse
import com.gamik.domain.usecase.HistoryUsecase
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
class HistoryViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Test
    fun get_history_success() {
        val today = DateUtil.today()
        val threeDaysAgo = DateUtil.threeDaysAgo()
        val rate = Pair("AUD", "1.278047")
        val historyResponse = HistoryResponse(rates = mapOf(Pair(today, mapOf(rate))))
        val historyRepository: HistoryRepositoryImpl = mock {
            on { getHistory(threeDaysAgo, today, "USD", "AUD") } doReturn flow {
                emit(Result.Success(historyResponse))
            }
        }

        val historyViewModel = HistoryViewModel(HistoryUsecase(historyRepository))
        historyViewModel.getHistory(threeDaysAgo, today, "USD", "AUD")
        val result = historyViewModel.historyViewState.value
        Assert.assertEquals(Result.Success(historyResponse), result)
        Assert.assertEquals(true, (result as Result.Success).data?.rates?.keys?.contains(today))
    }

    @Test
    fun get_history_failure() {
        val error = CallError.API_ERROR
        val today = DateUtil.today()
        val threeDaysAgo = DateUtil.threeDaysAgo()
        val historyRepository: HistoryRepositoryImpl = mock {
            on { getHistory(threeDaysAgo, today, "USD", "AUD") } doReturn flow {
                emit(Result.Failure(error))
            }
        }

        val historyViewModel = HistoryViewModel(HistoryUsecase(historyRepository))
        historyViewModel.getHistory(threeDaysAgo, today, "USD", "AUD")
        val result = historyViewModel.historyViewState.value
        Assert.assertEquals(Result.Failure(error), result)
        Assert.assertNotEquals(CallError.RATE_LIMIT_EXCEEDED, (result as Result.Failure).error)
    }
}