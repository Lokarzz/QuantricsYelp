package com.lokarz.gameforview.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {

 companion object {
     fun dateToString(date: Date?, myFormat: String?): String? {
         return dateToString(date, myFormat, true)
     }

     fun dateToString(date: Long, format: String?): String? {
         return dateToString(Date(date), format)
     }


     fun dateToString(date: Date?, myFormat: String?, isTranslate: Boolean): String? {
         var sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)

         sdf.timeZone = TimeZone.getTimeZone("Singapore")
         return sdf.format(date)
     }

     fun getDateToMillis(date: String?, format: String?): Long? {
         val sdf = SimpleDateFormat(format)
         var timeInMilliseconds: Long = 0
         try {
             val mDate = sdf.parse(date)
             timeInMilliseconds = mDate.time
         } catch (e: Exception) {
             // do nothing
         }
         return timeInMilliseconds
     }
 }
}