package com.lokarz.yelp.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    private const val TWELVE_HOUR_FORMAT = "hh:mm aa"
    private const val TWENTY_FOUR_HOUR_FORMAT = "HHmm"
    private const val EMPTY = ""

    fun to12HourFormat(twentyFourHour: String): String {
        var ret = EMPTY
        try {
            val sdf24 = SimpleDateFormat(TWENTY_FOUR_HOUR_FORMAT, Locale.getDefault())
            val sdf12 = SimpleDateFormat(TWELVE_HOUR_FORMAT, Locale.getDefault())
            val date = sdf24.parse(twentyFourHour)
            date?.let {
                ret = sdf12.format(it)
            }
        } catch (e: Exception) {
            // do nothing
        }
        return ret
    }

}