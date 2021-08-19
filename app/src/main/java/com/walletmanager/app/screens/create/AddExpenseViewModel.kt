package com.walletmanager.app.screens.create

import android.app.Application
import androidx.lifecycle.*
import com.walletmanager.app.database.entities.DbExpense
import com.walletmanager.app.database.dao.ExpenseDao
import com.walletmanager.app.database.dao.TransactionDao
import com.walletmanager.app.database.dao.YearDao
import com.walletmanager.app.database.entities.DbTransaction
import com.walletmanager.app.database.entities.DbYear
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class AddExpenseViewModel(
    val yearDao: YearDao,
    val transactionDao: TransactionDao,
    application: Application,
) :
    AndroidViewModel(application) {

    private val _addTransaction = MutableLiveData<Boolean>()

    val addTransaction: LiveData<Boolean>
        get() = _addTransaction

    init {
        _addTransaction.value = false
    }

    fun addExpenseAmount(dbYear: DbYear, dbTransaction: DbTransaction) {
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

class AddExpenseViewModelFactory(
    private val yearDao: YearDao,
    private val transactionDao: TransactionDao,
    private val application: Application,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddExpenseViewModel::class.java)) {
            return AddExpenseViewModel(yearDao, transactionDao, application) as T
        }
        throw IllegalArgumentException("No viewmodel found")
    }
}