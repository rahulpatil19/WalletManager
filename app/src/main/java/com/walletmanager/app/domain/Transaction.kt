package com.walletmanager.app.domain

import android.os.Parcelable
import androidx.lifecycle.Transformations.map
import com.walletmanager.app.database.entities.DbTransaction
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Transaction(
    val id: Int,
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
) : Parcelable


fun Transaction.asDbTransaction(): DbTransaction {
    return DbTransaction(id,
        category,
        amount,
        title,
        date,
        type,
        month,
        year,
        categoryIcon,
        categoryColor,
        createdDate)
}