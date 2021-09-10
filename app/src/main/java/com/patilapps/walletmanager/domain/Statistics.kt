package com.patilapps.walletmanager.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Statistics(
    val month: String,
    val totalAmount: Long,
    val totalIncome: Long,
    val totalExpense: Long,
    val totalTransactions: Int,
    val lastModified: String,
) : Parcelable