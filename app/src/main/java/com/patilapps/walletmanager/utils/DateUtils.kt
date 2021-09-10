package com.patilapps.walletmanager.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault())
    val date = Date()
    return dateFormat.format(date)
}

fun formatDate(date: Date): String {
    val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
    return dateFormat.format(date)
}

fun formatStringDate(dateString: String): String {
    val formatter = SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.getDefault())
    val date = formatter.parse(dateString)

    Log.d("DATE", dateString)

    date?.let {
        Log.d("DATE", date.toString())

        val newDateFormatter = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
        val formatedDate = newDateFormatter.format(date)
        return formatedDate
    }
    return ""
}

fun formatDateWithYear(date: Date): String {
    val dateFormat = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
    return dateFormat.format(date)
}

fun formatSelectedDate(date: Date): String {
    val dateFormat = SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault())
    return dateFormat.format(date)
}

fun getMonthString(month: Int): String {
    return when (month) {
        0 -> "Jan"
        1 -> "Feb"
        2 -> "Mar"
        3 -> "Apr"
        4 -> "May"
        5 -> "Jun"
        6 -> "Jul"
        7 -> "Aug"
        8 -> "Sep"
        9 -> "Oct"
        10 -> "Nov"
        11 -> "Dec"
        else -> "Jan"
    }
}