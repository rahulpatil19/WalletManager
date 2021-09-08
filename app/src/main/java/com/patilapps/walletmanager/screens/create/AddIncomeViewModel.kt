package com.patilapps.walletmanager.screens.create

import android.app.Application
import androidx.lifecycle.*
import com.patilapps.walletmanager.database.dao.TransactionDao
import com.patilapps.walletmanager.database.dao.YearDao
import com.patilapps.walletmanager.database.entities.DbTransaction
import com.patilapps.walletmanager.database.entities.DbYear
import kotlinx.coroutines.*
import java.lang.IllegalArgumentException

class AddIncomeViewModel(
    val yearDao: YearDao,
    val transactionDao: TransactionDao,
    application: Application,
) : ViewModel() {

    private val _addTransaction = MutableLiveData<Boolean>()
    val addTransaction: LiveData<Boolean>
        get() = _addTransaction

    init {
        _addTransaction.value = false
    }

    fun addIncomeAmount(dbYear: DbYear, dbTransaction: DbTransaction) {
        viewModelScope.launch {
            yearDao.insertYear(dbYear)
            transactionDao.insertTransaction(dbTransaction)
            _addTransaction.value = true
        }
    }

    fun onTransactionComplete() {
        _addTransaction.value = false
    }

}

class AddIncomeViewModelFactory(
    val yearDao: YearDao,
    private val transactionDao: TransactionDao,
    val application: Application,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddIncomeViewModel::class.java)) {
            return AddIncomeViewModel(yearDao, transactionDao, application) as T
        }
        throw IllegalArgumentException("No viewmodel found")
    }
}