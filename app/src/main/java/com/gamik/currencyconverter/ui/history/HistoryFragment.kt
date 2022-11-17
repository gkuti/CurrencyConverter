package com.gamik.currencyconverter.ui.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.gamik.currencyconverter.R
import com.gamik.currencyconverter.databinding.FragmentHistoryBinding
import com.gamik.currencyconverter.util.DateUtil
import com.gamik.currencyconverter.util.Message
import com.gamik.domain.util.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : Fragment(R.layout.fragment_history) {
    private lateinit var binding: FragmentHistoryBinding
    private val viewModel: HistoryViewModel by viewModels()
    private val args: HistoryFragmentArgs by navArgs()
    private lateinit var rates: Map<String, Map<String, String>>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHistoryBinding.bind(view)
        historyObserver()
        getHistory()
    }

    private fun getHistory() {
        viewModel.getHistory(
            DateUtil.threeDaysAgo(),
            DateUtil.today(),
            args.baseCurrency,
            "${args.targetCurrency}, ${args.symbols.toList().joinToString { it }}"
        )
    }

    private fun historyObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.historyViewState.collect { viewState ->
                    when (viewState) {
                        is Result.Success -> {
                            rates = viewState.data?.rates ?: emptyMap()
                            showHistory()
                        }
                        is Result.Failure -> {
                            Message.show(
                                requireContext(), viewState.error.message, getString(R.string.retry)
                            ) { _, _ -> getHistory() }
                        }
                        Result.Loading -> {
                            Message.show(
                                requireContext(), getString(R.string.getting_details)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun showHistory() {
        val todayRate = rates[DateUtil.today()]
        val otherCurrencies =
            todayRate?.keys?.filterNot { it == args.targetCurrency }
                ?.joinToString("\n") {
                    "$it: ${todayRate[it]}"

                }
        val threeDayRate =
            rates.keys.joinToString("\n") {
                "$it: ${rates[it]?.get(args.targetCurrency)}"
            }
        binding.threeDayRate.text = threeDayRate
        binding.otherCurrencies.text = otherCurrencies
    }
}
