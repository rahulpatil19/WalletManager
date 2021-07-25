package com.walletmanager.app.domain

data class Expense(
    val id: Int,
    val category: String,
    val amount: Long,
    val title: String,
    val date: String
) {
}