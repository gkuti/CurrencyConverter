package com.gamik.currencyconverter.util

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtil {
    fun today(): String {
        val simpleDateFormat = SimpleDateFormat.getDateInstance() as SimpleDateFormat
        simpleDateFormat.applyPattern("yyyy-MM-dd")
        simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return simpleDateFormat.format(Calendar.getInstance().time)
    }

    fun threeDaysAgo(): String {
        val simpleDateFormat = SimpleDateFormat.getDateInstance() as SimpleDateFormat
        simpleDateFormat.applyPattern("yyyy-MM-dd")
        simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val threeDaysAgo = Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(2))
        return simpleDateFormat.format(threeDaysAgo.time)
    }
}