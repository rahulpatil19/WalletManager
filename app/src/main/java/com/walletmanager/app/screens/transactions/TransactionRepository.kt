package com.walletmanager.app.screens.transactions

import com.walletmanager.app.database.dao.TransactionDao
import com.walletmanager.app.database.entities.asTransaction
import com.walletmanager.app.domain.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepository(transactionDao: TransactionDao) {

    val dbTransactionList: Flow<List<Transaction>> = transactionDao.getAllTransaction().map {
        it.asTransaction()
    }
}