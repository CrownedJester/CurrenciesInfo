package com.crownedjester.soft.currenciesinfo.domain.repository.local

import com.crownedjester.soft.currenciesinfo.domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    fun loadCurrenciesCache(): Flow<List<Currency>>

    suspend fun saveCurrenciesCache(data: List<Currency>)

    suspend fun clearCache(): Boolean

}