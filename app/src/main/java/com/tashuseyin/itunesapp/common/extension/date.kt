package com.tashuseyin.itunesapp.common.extension

import java.text.SimpleDateFormat
import java.util.*

fun dateToString(date: String): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
    return dateFormat.format(format.parse(date))
}
