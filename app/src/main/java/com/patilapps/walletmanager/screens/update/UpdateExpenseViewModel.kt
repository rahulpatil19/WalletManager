package com.patilapps.walletmanager.screens.update

import android.app.Application
import androidx.lifecycle.*
import com.patilapps.walletmanager.database.dao.TransactionDao
import com.patilapps.walletmanager.database.dao.YearDao
import com.patilapps.walletmanager.database.entities.DbTransaction
import com.patilapps.walletmanager.database.entities.DbYear
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class UpdateExpenseViewModel(
    application: Application,
    val transactionDao: TransactionDao,
    val yearDao: YearDao,
) : AndroidViewModel(application) {

    private var _updateTransaction = MutableLiveData<Boolean>()

    val updateTransaction: LiveData<Boolean>
        get() = _updateTransaction

    init {
        _updateTransaction.value = false
    }

    fun updateTransaction(dbTransaction: DbTransaction, dbYear: DbYear) {
        viewModelScope.launch {
            transactionDao.updateTransaction(dbTransaction)
            yearDao.updateTransactionYear(dbYear)
            _updateTransaction.value = true
        }
    }
}

class UpdateExpenseViewModelFactory(
    val application: Application,
    val transactionDao: TransactionDao,
    val yearDao: YearDao,
) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(UpdateExpenseViewModel::class.java)){
            return UpdateExpenseViewModel(application, transactionDao, yearDao) as T
        }
        throw IllegalArgumentException("ViewModel not found")
    }
}