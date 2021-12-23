package com.crownedjester.soft.currenciesinfo.data

import com.crownedjester.soft.currenciesinfo.data.model.CurrencyResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface NBRBServiceApi {

    @GET("/Services/XmlExRates.aspx")
    suspend fun getCurrenciesData(@Query("ondate") date: String): CurrencyResponse

}

fun createClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY

    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

}