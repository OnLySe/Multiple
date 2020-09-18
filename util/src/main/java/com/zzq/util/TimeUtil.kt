package com.zzq.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {

    private val TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private val DAY_FORMAT = "yyyy-MM-dd"
    private val dayFormat = SimpleDateFormat(DAY_FORMAT)
    private val dateFormat1 = SimpleDateFormat(TIME_FORMAT)

    fun getTodayString(): String {
        return dayFormat.format(Date())
    }

    fun getDate1(): String {
        return dateFormat1.format(Date())
    }
}