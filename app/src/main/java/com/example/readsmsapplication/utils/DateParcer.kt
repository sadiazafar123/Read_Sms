package com.example.readsmsapplication.utils

import java.text.SimpleDateFormat
import java.util.*

object DateParcer {
    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm a")
        return format.format(date)
    }
}