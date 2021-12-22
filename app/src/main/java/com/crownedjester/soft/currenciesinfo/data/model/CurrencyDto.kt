package com.crownedjester.soft.currenciesinfo.data.model

import com.crownedjester.soft.currenciesinfo.domain.model.Currency
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "Currency", strict = false)
data class CurrencyDto @JvmOverloads constructor(

    @field:Element(name = "NumCode")
    var numCode: Int? = null,

    @field:Element(name = "CharCode")
    var charCode: String? = null,

    @field:Element(name = "Scale")
    var scale: Int? = null,

    @field:Element(name = "Name")
    var name: String? = null,

    @field:Element(name = "Rate")
    var rate: Double? = null
)

fun CurrencyDto.toCurrency(): Currency {
    return Currency(
        numCode!!, charCode!!, scale!!, name!!, rate!!
    )
}
