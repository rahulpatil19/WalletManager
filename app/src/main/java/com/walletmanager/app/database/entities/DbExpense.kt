package com.walletmanager.app.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.walletmanager.app.database.DateConverter
import java.util.*

@Entity(tableName = "table_expense")
@TypeConverters(DateConverter::class)
data class DbExpense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String,
    val amount: Long,
    val title: String,
    val date: Date,
    val type: String,
    val month: String,
    val year: Int,
    val categoryIcon: Int,
    val categoryColor: Int,
    val createdDate: Date
)