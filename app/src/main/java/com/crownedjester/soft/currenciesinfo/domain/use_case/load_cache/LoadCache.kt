package com.crownedjester.soft.currenciesinfo.domain.use_case.load_cache

import com.crownedjester.soft.currenciesinfo.domain.repository.local.LocalRepository
import javax.inject.Inject

class LoadCache @Inject constructor(private val repository: LocalRepository) {

    operator fun invoke() = repository.loadCurrenciesCache()

}