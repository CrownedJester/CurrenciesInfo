package com.crownedjester.soft.currenciesinfo.data

import com.crownedjester.soft.currenciesinfo.data.model.CurrencyDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NBRBServiceApi {

    @GET("https://www.nbrb.by/Services/XmlExRates.aspx")
    suspend fun getCurrenciesData(@Query("ondate") date: String): List<CurrencyDto>
}