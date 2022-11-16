package com.gamik.currencyconverter.di

import com.gamik.data.remote.CurrencyService
import com.gamik.data.repository.ConverterRepositoryImpl
import com.gamik.domain.repository.ConverterRepository
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
    fun Repo(
        currencyService: CurrencyService,
    ): ConverterRepository =
        ConverterRepositoryImpl(currencyService)
}