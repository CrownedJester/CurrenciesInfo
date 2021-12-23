package com.crownedjester.soft.currenciesinfo.representation.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    private const val sdfPatternSend = "MM.dd.yyyy"
    private const val sdfPatternDeploy = "dd.MM.yyyy"

    const val MODE_SEND = 0
    const val MODE_DEPLOY = 1


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

    fun getTomorrowDate(mode: Int): String {
        val calendar = Calendar.getInstance()
        val sdf: SimpleDateFormat
        if (mode == MODE_SEND) {
            sdf = SimpleDateFormat(sdfPatternSend, Locale.getDefault())
        } else {
            sdf = SimpleDateFormat(sdfPatternDeploy, Locale.getDefault())
        }
        calendar.add(Calendar.DATE, +1)
        return sdf.format(calendar.time)
    }

    fun getYesterdayDate(mode: Int): String {
        val calendar = Calendar.getInstance()
        val sdf: SimpleDateFormat
        if (mode == MODE_SEND) {
            sdf = SimpleDateFormat(sdfPatternSend, Locale.getDefault())
        } else {
            sdf = SimpleDateFormat(sdfPatternDeploy, Locale.getDefault())
        }
        calendar.add(Calendar.DATE, -1)
        return sdf.format(calendar.time)
    }
}