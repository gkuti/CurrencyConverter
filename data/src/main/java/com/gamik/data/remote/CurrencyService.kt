package com.gamik.data.remote

import com.gamik.domain.model.ConvertResponse
import com.gamik.domain.model.SymbolResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {

    @GET("symbols")
    suspend fun getSymbols(): SymbolResponse

    @GET("convert")
    suspend fun convert(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    ): ConvertResponse
}