package com.crownedjester.soft.currenciesinfo.domain.use_case

import com.crownedjester.soft.currenciesinfo.domain.use_case.get_currencies.GetCurrenciesData
import com.crownedjester.soft.currenciesinfo.domain.use_case.load_cache.LoadCache
import com.crownedjester.soft.currenciesinfo.domain.use_case.save_cache.SaveCache
import javax.inject.Inject

data class UseCases @Inject constructor(
    val getCurrenciesData: GetCurrenciesData,
    val loadCache: LoadCache,
    val saveCache: SaveCache
)