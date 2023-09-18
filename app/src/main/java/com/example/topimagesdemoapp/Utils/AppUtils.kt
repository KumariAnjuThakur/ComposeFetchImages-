package com.example.topimagesdemoapp.Utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun convertTimestampToDate(timestamp: Long?): String {
    timestamp?.let {
        val dt = Date(timestamp * 1000)
        val sdf = SimpleDateFormat("dd/MM/yy hh:mm aa", Locale.ENGLISH)
        return sdf.format(dt)
    }
    return ""
}