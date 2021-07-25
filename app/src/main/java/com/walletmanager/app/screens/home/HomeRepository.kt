package com.walletmanager.app.screens.home

import com.walletmanager.app.database.dao.TransactionDao
import com.walletmanager.app.database.entities.asTransaction
import com.walletmanager.app.domain.Transaction
import kotlinx.coroutines.flow.*

class HomeRepository(val transactionDao: TransactionDao) {

    val dbTransactionList: Flow<List<Transaction>> =
        transactionDao.getAllTransaction().map {
            it.asTransaction()
                .asReversed()
        }
}