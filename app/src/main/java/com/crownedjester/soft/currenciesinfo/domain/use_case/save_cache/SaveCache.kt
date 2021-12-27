package com.crownedjester.soft.currenciesinfo.domain.use_case.save_cache

import com.crownedjester.soft.currenciesinfo.domain.LocalRepository
import com.crownedjester.soft.currenciesinfo.domain.model.Currency
import javax.inject.Inject

class SaveCache @Inject constructor(private val repository: LocalRepository) {

    operator fun invoke(data: List<Currency>, dir: String) =
        repository.saveCache(data, dir)
}