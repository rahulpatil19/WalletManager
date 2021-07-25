package com.walletmanager.app.screens.create

import android.app.Application
import androidx.lifecycle.*
import com.walletmanager.app.database.dao.IncomeDao
import com.walletmanager.app.database.dao.TransactionDao
import com.walletmanager.app.database.entities.DbIncome
import com.walletmanager.app.database.entities.DbTransaction
import kotlinx.coroutines.*
import java.lang.IllegalArgumentException

class AddIncomeViewModel(
    val incomeDao: IncomeDao,
    val transactionDao: TransactionDao,
    application: Application,
) : ViewModel() {

    private val _addTransaction = MutableLiveData<Boolean>()
    val addTransaction: LiveData<Boolean>
        get() = _addTransaction

    init {
        _addTransaction.value = false
    }

    fun addIncomeAmount(dbIncome: DbIncome, dbTransaction: DbTransaction) {
        viewModelScope.launch {
            incomeDao.insertIncome(dbIncome)
            transactionDao.insertTransaction(dbTransaction)
            _addTransaction.value = true
        }
    }

    fun onTransactionComplete() {
        _addTransaction.value = false
    }

}

class AddIncomeViewModelFactory(
    val incomeDao: IncomeDao,
    private val transactionDao: TransactionDao,
    val application: Application,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddIncomeViewModel::class.java)) {
            return AddIncomeViewModel(incomeDao, transactionDao, application) as T
        }
        throw IllegalArgumentException("No viewmodel found")
    }
}