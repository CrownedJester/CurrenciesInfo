package com.crownedjester.soft.currenciesinfo.data.model

import com.crownedjester.soft.currenciesinfo.domain.model.Currency
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "Currency", strict = false)
data class CurrencyDto(
    @Element(name = "NumCode")
    val numCode: Int,
    @Element(name = "CharCode")
    val charCode: String,
    @Element(name = "Scale")
    val scale: Int,
    @Element(name = "Name")
    val name: String,
    @Element(name = "Rate")
    val rate: Double
)

fun CurrencyDto.toCurrency(): Currency {
    return Currency(
        numCode, charCode, scale, name, rate
    )
}
