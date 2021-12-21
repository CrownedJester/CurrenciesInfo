package com.crownedjester.soft.currenciesinfo.data

import com.crownedjester.soft.currenciesinfo.data.model.CurrencyDto

interface RemoteServiceRepository {

    suspend fun getCurrenciesData(date: String): List<CurrencyDto>
}