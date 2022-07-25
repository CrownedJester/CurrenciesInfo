package com.crownedjester.soft.currenciesinfo.domain.repository.local

import com.crownedjester.soft.currenciesinfo.domain.data_source.CurrencyDao
import com.crownedjester.soft.currenciesinfo.domain.model.Currency
import kotlinx.coroutines.flow.Flow

class LocalRepositoryImpl(private val dao: CurrencyDao) : LocalRepository {
    override fun loadCurrenciesCache(): Flow<List<Currency>> =
        dao.retrieveCurrencies()

    override suspend fun saveCurrenciesCache(data: List<Currency>) {
        data.forEach {
            dao.addCurrency(it)
        }
    }

    override suspend fun clearCache(): Boolean =
        dao.clearCache()

}