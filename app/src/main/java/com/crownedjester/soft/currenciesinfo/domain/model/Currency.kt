package com.crownedjester.soft.currenciesinfo.domain.model

data class Currency(
    val numCode: Int,
    val charCode: String,
    val scale: Int,
    val name: String,
    val rate: Double,
    var isTracking: Boolean = false,
    var alternativeRate: Double = 0.0,
    var position: Int = 0
)
