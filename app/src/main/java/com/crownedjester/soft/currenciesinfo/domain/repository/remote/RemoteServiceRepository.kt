package com.crownedjester.soft.currenciesinfo.domain.repository.remote

import com.crownedjester.soft.currenciesinfo.data.model.CurrencyDto

interface RemoteServiceRepository {

    suspend fun getCurrenciesData(date: String): List<CurrencyDto>
}