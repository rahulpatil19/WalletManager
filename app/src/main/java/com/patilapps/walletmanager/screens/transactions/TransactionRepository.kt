package com.patilapps.walletmanager.screens.transactions

import com.patilapps.walletmanager.database.dao.TransactionDao
import com.patilapps.walletmanager.database.entities.asTransaction
import com.patilapps.walletmanager.domain.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepository(transactionDao: TransactionDao) {

    val dbTransactionList: Flow<List<Transaction>> = transactionDao.getTenRecentTransactions().map {
        it.asTransaction()
    }
}