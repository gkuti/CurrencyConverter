package com.gamik.currencyconverter.di

import com.gamik.data.remote.CurrencyService
import com.gamik.data.repository.ConverterRepositoryImpl
import com.gamik.data.repository.HistoryRepositoryImpl
import com.gamik.domain.repository.ConverterRepository
import com.gamik.domain.repository.HistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun converterRepo(
        currencyService: CurrencyService,
    ): ConverterRepository =
        ConverterRepositoryImpl(currencyService)

    @Singleton
    @Provides
    fun historyRepo(
        currencyService: CurrencyService,
    ): HistoryRepository =
        HistoryRepositoryImpl(currencyService)
}