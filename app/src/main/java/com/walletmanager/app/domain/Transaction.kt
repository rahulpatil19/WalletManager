package com.walletmanager.app.domain

import android.os.Parcelable
import java.util.*

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
    val createdDate: Date
)
