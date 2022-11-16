package com.gamik.currencyconverter.ui.converter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.gamik.currencyconverter.R
import com.gamik.currencyconverter.databinding.FragmentConverterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConverterFragment : Fragment(R.layout.fragment_converter) {
    private lateinit var binding: FragmentConverterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentConverterBinding.bind(view)
    }
}
