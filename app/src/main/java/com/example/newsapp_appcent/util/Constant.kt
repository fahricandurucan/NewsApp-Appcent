package com.example.newsapp_appcent.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Constant {
    companion object{
        const val API_KEY ="67ac14d0bf624da1a49b791e4f24089e"
        const val BASE_URL ="https://newsapi.org/"
        const val SEARCH_NEWS_TIME_DELAY = 500L
        const val QUERY_PAGE_SIZE = 2


        @JvmStatic
        fun convertDate(dateTime: String?): String {
            val date = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME)

            return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        }

        fun convertDateTime(date:String?):String{
            val dateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)

            val year = dateTime.year
            val month = dateTime.monthValue
            val day = dateTime.dayOfMonth
            val hour = dateTime.hour
            val minute = dateTime.minute

            return "$day-$month-$year $hour:$minute"
        }
    }
}