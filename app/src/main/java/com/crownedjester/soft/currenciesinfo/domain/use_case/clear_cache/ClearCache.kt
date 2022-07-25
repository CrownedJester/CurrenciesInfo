package com.crownedjester.soft.currenciesinfo.domain.use_case.clear_cache

import com.crownedjester.soft.currenciesinfo.domain.repository.local.LocalRepository
import javax.inject.Inject

class ClearCache @Inject constructor(private val repository: LocalRepository) {

    suspend operator fun invoke() =
        repository.clearCache()

}