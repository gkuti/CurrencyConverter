package com.gamik.data.remote

import com.gamik.domain.model.ConvertResponse
import com.gamik.domain.model.HistoryResponse
import com.gamik.domain.model.SymbolResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {

    @GET("symbols")
    suspend fun getSymbols(): Response<SymbolResponse>

    @GET("convert")
    suspend fun convert(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    ): Response<ConvertResponse>

    @GET("timeseries")
    suspend fun history(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): Response<HistoryResponse>
}