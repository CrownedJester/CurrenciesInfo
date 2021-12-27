package com.crownedjester.soft.currenciesinfo.domain.use_case.load_cache

import com.crownedjester.soft.currenciesinfo.domain.LocalRepository
import com.crownedjester.soft.currenciesinfo.domain.model.Currency
import java.io.File
import javax.inject.Inject

class LoadCache @Inject constructor(private val repository: LocalRepository) {

    operator fun invoke(file: File): List<Currency> {
        return repository.loadCache(file)
    }
}