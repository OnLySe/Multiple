package com.zzq.common.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {

    private val TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private val MINUTE_FORMAT = "yyyy-MM-dd HH:mm"
    private val HOUR_FORMAT = "yyyy-MM-dd HH"
    private val DAY_FORMAT = "yyyy-MM-dd"
    private val dayFormat = SimpleDateFormat(DAY_FORMAT)
    private val minuteFormat = SimpleDateFormat(MINUTE_FORMAT)
    private val hourFormat = SimpleDateFormat(HOUR_FORMAT)
    private val timeFormat = SimpleDateFormat(TIME_FORMAT)

    fun getTodayString(): String {
        return dayFormat.format(Date())
    }

    fun getTimeString(): String {
        return timeFormat.format(Date())
    }

    fun getMinuteString(): String {
        return minuteFormat.format(Date())
    }

    fun getHourString(): String {
        return hourFormat.format(Date())
    }
}