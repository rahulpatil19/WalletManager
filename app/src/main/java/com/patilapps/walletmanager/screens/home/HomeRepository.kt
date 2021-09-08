package com.patilapps.walletmanager.screens.home

import com.patilapps.walletmanager.database.dao.TransactionDao
import com.patilapps.walletmanager.database.entities.asTransaction
import com.patilapps.walletmanager.domain.Transaction
import kotlinx.coroutines.flow.*

class HomeRepository(val transactionDao: TransactionDao) {

    val dbTransactionList: Flow<List<Transaction>> =
        transactionDao.getTenRecentTransactions().map {
            it.asTransaction()
                .asReversed()
        }.take(10)
}