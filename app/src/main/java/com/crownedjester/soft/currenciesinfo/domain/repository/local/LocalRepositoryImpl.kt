package com.crownedjester.soft.currenciesinfo.domain.repository.local

import com.crownedjester.soft.currenciesinfo.domain.data_source.CurrencyDao
import com.crownedjester.soft.currenciesinfo.domain.model.CachedCurrency
import kotlinx.coroutines.flow.Flow

class LocalRepositoryImpl(private val dao: CurrencyDao) : LocalRepository {
    override fun loadCurrenciesCache(): Flow<List<CachedCurrency>> =
        dao.retrieveCurrencies()

    override suspend fun saveCurrenciesCache(vararg data: CachedCurrency) {
        dao.saveCurrencies(*data)
    }

    override suspend fun clearCache(): Int =
        dao.clearCache()

}