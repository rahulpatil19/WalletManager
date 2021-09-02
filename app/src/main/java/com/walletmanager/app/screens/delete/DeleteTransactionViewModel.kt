package com.walletmanager.app.screens.delete

import android.app.Application
import androidx.lifecycle.*
import com.walletmanager.app.database.dao.TransactionDao
import com.walletmanager.app.database.entities.DbTransaction
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class DeleteTransactionViewModel(application: Application, val transactionDao: TransactionDao) :
    AndroidViewModel(application) {

    private var _deleteTransaction = MutableLiveData<Boolean>()

    val deleteTransaction: LiveData<Boolean>
        get() = _deleteTransaction

    init {
        _deleteTransaction.value = false
    }

    fun deleteTransaction(dbTransaction: DbTransaction) {
        viewModelScope.launch {
            transactionDao.deleteTransaction(dbTransaction)
            _deleteTransaction.value = true
        }
    }
}


class DeleteTransactionViewModelFactory(val application: Application, val transactionDao: TransactionDao) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeleteTransactionViewModel::class.java)){
            return DeleteTransactionViewModel(application, transactionDao) as T
        }
        throw IllegalArgumentException("Viewmodel not found")
    }
}