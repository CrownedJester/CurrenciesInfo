package com.crownedjester.soft.currenciesinfo.representation.viewmodel

import com.crownedjester.soft.currenciesinfo.domain.model.Currency

data class CurrenciesState(
    val data: List<Currency>? = emptyList(),
    val error: String = ""
)
