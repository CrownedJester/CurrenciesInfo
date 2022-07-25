package com.crownedjester.soft.currenciesinfo.domain.repository.local

import com.crownedjester.soft.currenciesinfo.domain.model.CachedCurrency
import kotlinx.coroutines.flow.Flow


interface LocalRepository {

    fun loadCurrenciesCache(): Flow<List<CachedCurrency>>

    suspend fun clearCache(): Int

    suspend fun saveCurrenciesCache(vararg data: CachedCurrency)
}