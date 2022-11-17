package com.gamik.currencyconverter.util

import org.junit.Assert
import org.junit.Test

class DateUtilTest {

    @Test
    fun today() {
        val today = DateUtil.today()
        val regex = Regex("((\\d){4}-(\\d){2}-(\\d){2})")
        Assert.assertEquals(true, regex.matches(today))
    }

    @Test
    fun threeDaysAgo() {
        val threeDaysAgo = DateUtil.threeDaysAgo()
        val regex = Regex("((\\d){4}-(\\d){2}-(\\d){2})")
        Assert.assertEquals(true, regex.matches(threeDaysAgo))
    }
}