package com.walletmanager.app.database

import androidx.room.TypeConverter
import java.util.*

class DateConverter{
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return if (date == null) null else date.getTime()
    }
}