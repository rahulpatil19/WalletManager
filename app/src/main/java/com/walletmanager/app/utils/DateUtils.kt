package com.walletmanager.app.utils

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault())
    val date = Date()
    return dateFormat.format(date)
}

fun formatDate(date: Date): String{
    val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
    return dateFormat.format(date)
}

fun formatDateWithYear(date: Date): String{
    val dateFormat = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
    return dateFormat.format(date)
}

fun formatSelectedDate(date: Date): String{
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