package com.gamik.currencyconverter.ui.converter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.gamik.currencyconverter.R
import com.gamik.currencyconverter.databinding.FragmentConverterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConverterFragment : Fragment(R.layout.fragment_converter) {
    private lateinit var binding: FragmentConverterBinding
    private lateinit var symbols: List<String>
    private val viewModel: ConverterViewModel by viewModels()
    private var currentRate = 0.0

    private val baseTextListener: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            //Not required for functionality
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            binding.targetValue.removeTextChangedListener(targetTextListener)
            binding.targetValue.setText(
                if (binding.baseValue.text.isBlank()) "0.0" else
                    (binding.baseValue.text.toString().toDouble() * currentRate).toString()
            )
            binding.targetValue.addTextChangedListener(targetTextListener)
        }

        override fun afterTextChanged(p0: Editable?) {
            //Not required for functionality
        }
    }

    private val targetTextListener: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            //Not required for functionality
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            binding.baseValue.removeTextChangedListener(baseTextListener)
            binding.baseValue.setText(
                if (binding.targetValue.text.isBlank()) "0.0" else
                    (binding.targetValue.text.toString().toDouble() / currentRate).toString()
            )
            binding.baseValue.addTextChangedListener(baseTextListener)
        }

        override fun afterTextChanged(p0: Editable?) {
            //Not required for functionality
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentConverterBinding.bind(view)
        setUpObserver()
        setUpClickListeners()
        setUpTextChangeListeners()
    }

    private fun setUpObserver() {
        symbolObserver()
        rateObserver()
    }

    private fun setUpClickListeners() {
        binding.swap.setOnClickListener {
            val temp = binding.baseCurrency.selectedItemPosition
            binding.baseCurrency.setSelection(binding.targetCurrency.selectedItemPosition)
            binding.targetCurrency.setSelection(temp)
        }
        binding.details.setOnClickListener {
            launchHistoryFragment()
        }
    }

    private fun launchHistoryFragment() {
        val action =
            ConverterFragmentDirections.actionConverterFragmentToHistoryFragment(
                binding.baseCurrency.selectedItem.toString(),
                binding.targetCurrency.selectedItem.toString(),
                symbols.filterNot {
                    it == binding.baseCurrency.selectedItem.toString() ||
                            it == binding.targetCurrency.selectedItem.toString()
                }
                    .take(10).toTypedArray()
            )
        findNavController().navigate(action)
    }

    private fun setUpTextChangeListeners() {
        binding.baseValue.addTextChangedListener(baseTextListener)
        binding.targetValue.addTextChangedListener(targetTextListener)
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

    private fun rateObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.rateViewState.collect { viewState ->
                    when (viewState) {
                        is RatesViewState.Success -> {
                            currentRate = viewState.rate
                            updateTarget()
                        }
                        RatesViewState.Error -> {

                        }
                        RatesViewState.Loading -> {

                        }
                    }
                }
            }
        }
    }

    private fun setSymbolListeners() {
        binding.baseCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                getRates()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        binding.targetCurrency.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                getRates()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun getRates() {
        if (binding.baseCurrency.selectedItemPosition != binding.targetCurrency.selectedItemPosition) {
            //Using 1 as amount so as to not always do an api call if currency doesn't change
            //Conversion of other values will be done locally
            //NB:Depending on the business requirement this may change
            viewModel.convert(
                binding.baseCurrency.selectedItem.toString(),
                binding.targetCurrency.selectedItem.toString(),
                1.0
            )
        } else {
            binding.targetValue.text = binding.baseValue.text
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

    private fun updateTarget() {
        //Skip first initialization as initialize in the view model
        if (currentRate == -0.0) return
        binding.targetValue.setText(
            (binding.baseValue.text.toString().toDouble() * currentRate).toString()
        )
    }
}
