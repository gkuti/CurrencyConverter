package com.gamik.currencyconverter.ui.converter

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gamik.currencyconverter.R
import com.gamik.currencyconverter.databinding.FragmentConverterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConverterFragment : Fragment(R.layout.fragment_converter) {
    private lateinit var binding: FragmentConverterBinding
    private lateinit var symbols: List<String>
    private val viewModel: ConverterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentConverterBinding.bind(view)
        setUpObserver()
        setUpClickListeners()
    }

    private fun setUpObserver() {
        symbolObserver()
    }

    private fun setUpClickListeners() {
        binding.swap.setOnClickListener {
            val temp = binding.baseCurrency.selectedItemPosition
            binding.baseCurrency.setSelection(binding.targetCurrency.selectedItemPosition)
            binding.targetCurrency.setSelection(temp)
        }
    }

    private fun symbolObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.symbolViewState.collect { viewState ->
                    when (viewState) {
                        is SymbolViewState.Success -> {
                            symbols = viewState.response.symbols.keys.toList()
                            populateCurrencyList()
                        }
                        SymbolViewState.Error -> {

                        }
                        SymbolViewState.Loading -> {

                        }
                    }
                }
            }
        }
    }

    private fun setSymbolListeners() {
        binding.baseCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        binding.targetCurrency.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun populateCurrencyList() {
        ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_item, symbols
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.targetCurrency.adapter = adapter
            binding.baseCurrency.adapter = adapter
        }
        binding.targetCurrency.setSelection(0, false)
        binding.baseCurrency.setSelection(0, false)
        setSymbolListeners()
    }
}
