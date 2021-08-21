package com.walletmanager.app.screens.transactions

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.*
import com.walletmanager.app.database.dao.TransactionDao
import com.walletmanager.app.domain.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.IllegalArgumentException

class AllTransactionViewModel(
    private val transactionRepository: TransactionRepository,
    val transactionDao: TransactionDao,
) : ViewModel() {

    private var _navigateToMonth = MutableLiveData<Boolean>()

    val navigateToMonth: LiveData<Boolean>
        get() = _navigateToMonth

    val transactionByPage: Flow<PagingData<TransactionModel>> =
        Pager(PagingConfig(pageSize = 10, enablePlaceholders = false, prefetchDistance = 3)) {
            transactionDao.getTransactionPageSortedByDate()
        }.flow.map { pagingData ->
            pagingData.map { dbTransaction ->
                TransactionModel.TransactionData(dbTransaction)
            }.insertSeparators { beforeModel: TransactionModel?, afterModel: TransactionModel? ->
                when {
                    beforeModel == null -> {
                        afterModel?.let {
                            val afterData = afterModel as TransactionModel.TransactionData
                            val afterDate = "${afterData.transaction.month}, ${afterData.transaction.year}"
                            TransactionModel.HeaderData(afterDate)
                        }
                    }
                    afterModel == null -> null
                    else -> {
                        val beforeData = beforeModel as TransactionModel.TransactionData
                        val afterData = afterModel as TransactionModel.TransactionData
                        val newMonth = "${afterData.transaction.month}, ${afterData.transaction.year}"
                        val oldMonth = "${beforeData.transaction.month}, ${beforeData.transaction.year}"
                        when {
                            newMonth != oldMonth -> {
                                TransactionModel.HeaderData(newMonth)
                            }
                            else -> {
                                null
                            }
                        }
                    }
                }
            }
        }.cachedIn(viewModelScope)

    init {
        _navigateToMonth.value = false
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("Transaction Viewmodel", "Viewmodel cleared")
    }

    fun navigatedToMonth() {

    }
}

class AllTransactionViewModelFactory(
    private val transactionRepository: TransactionRepository,
    val transactionDao: TransactionDao,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllTransactionViewModel::class.java)) {
            return AllTransactionViewModel(transactionRepository, transactionDao) as T
        }
        throw IllegalArgumentException("No viewmodel found")
    }
}