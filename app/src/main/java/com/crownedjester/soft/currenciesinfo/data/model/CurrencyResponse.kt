package com.crownedjester.soft.currenciesinfo.data.model

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "DailyExRates", strict = false)
data class CurrencyResponse @JvmOverloads constructor(

    @field:ElementList(name = "Currency", inline = true, required = false)
    var currenciesData: MutableList<CurrencyDto> = mutableListOf()

)
