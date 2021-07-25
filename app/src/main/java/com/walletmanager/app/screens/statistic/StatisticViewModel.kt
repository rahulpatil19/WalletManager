package com.walletmanager.app.screens.statistic

import android.app.Application
import androidx.lifecycle.*
import com.walletmanager.app.database.dao.TransactionDao
import com.walletmanager.app.database.entities.asTransaction
import com.walletmanager.app.domain.Transaction
import java.lang.IllegalArgumentException

class HistoryViewModel(val transactionDao: TransactionDao): ViewModel() {

    val transactionList : LiveData<List<Transaction>> = transactionDao.getAllTransaction().asLiveData().map {
        it.asTransaction()
    }
}

class HistoryViewModelFactory(val application: Application, private val transactionDao: TransactionDao) :
    ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)){
            return HistoryViewModel(transactionDao) as T
        }
        throw IllegalArgumentException("No viewmodel found")
    }
}