package com.gamik.currencyconverter.network

import com.gamik.currencyconverter.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AuthInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val requestBuilder: Request.Builder = original.newBuilder()
            .header("apikey", BuildConfig.API_KEY)
        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}