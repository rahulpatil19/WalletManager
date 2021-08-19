package com.walletmanager.app.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.walletmanager.app.database.DateConverter
import com.walletmanager.app.domain.Transaction
import java.util.*

@Entity(tableName = "table_all_transaction")
@TypeConverters(DateConverter::class)
data class DbTransaction(
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
    val createdDate: Date,
)

fun List<DbTransaction>.asTransaction(): List<Transaction> {
    return map { dbTransaction ->
        Transaction(
            id = dbTransaction.id,
            category = dbTransaction.category,
            amount = dbTransaction.amount,
            title = dbTransaction.title,
            date = dbTransaction.date,
            type = dbTransaction.type,
            month = dbTransaction.month,
            year = dbTransaction.year,
            categoryIcon = dbTransaction.categoryIcon,
            categoryColor = dbTransaction.categoryColor,
            createdDate = dbTransaction.createdDate)
    }
}