package com.crownedjester.soft.currenciesinfo.representation.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    private const val sdfPatternSend = "MM.dd.yyyy"
    private const val sdfPatternDeploy = "dd.MM.yyyy"

    const val MODE_SEND = 0
    const val MODE_DEPLOY = 1
    const val TOMORROW_CODE = 0
    const val YESTERDAY_CODE = 1


    fun getCurrentDate(mode: Int): String {
        val date = Calendar.getInstance().time
        val sdf: SimpleDateFormat
        if (mode == MODE_SEND) {
            sdf = SimpleDateFormat(sdfPatternSend, Locale.getDefault())
        } else {
            sdf = SimpleDateFormat(sdfPatternDeploy, Locale.getDefault())
        }
        return sdf.format(date)
    }

    fun getAlternativeDate(mode: Int, dateCode: Int): String {
        val calendar = Calendar.getInstance()
        val sdf = if (mode == MODE_SEND) {
            SimpleDateFormat(sdfPatternSend, Locale.getDefault())
        } else {
            SimpleDateFormat(sdfPatternDeploy, Locale.getDefault())
        }
        calendar.add(Calendar.DATE, if (dateCode == TOMORROW_CODE) +1 else -1)
        return sdf.format(calendar.time)
    }

}