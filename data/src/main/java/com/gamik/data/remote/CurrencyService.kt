package com.gamik.data.remote

import com.gamik.domain.model.SymbolResponse
import retrofit2.http.GET

interface CurrencyService {

    @GET("symbols")
    suspend fun getSymbols(): SymbolResponse
}