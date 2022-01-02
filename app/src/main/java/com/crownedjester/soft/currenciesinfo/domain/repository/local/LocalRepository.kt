package com.crownedjester.soft.currenciesinfo.domain.repository.local

import com.crownedjester.soft.currenciesinfo.domain.model.Currency
import java.io.File

interface LocalRepository {

    fun loadCache(file: File): List<Currency>

    fun saveCache(data: List<Currency>, dir: String): Boolean
}