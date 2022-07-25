package com.crownedjester.soft.currenciesinfo.domain.use_case.save_cache

import com.crownedjester.soft.currenciesinfo.domain.model.Currency
import com.crownedjester.soft.currenciesinfo.domain.repository.local.LocalRepository
import javax.inject.Inject

class SaveCache @Inject constructor(private val repository: LocalRepository) {

    suspend operator fun invoke(data: List<Currency>) =
        repository.saveCurrenciesCache(data)

}