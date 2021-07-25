package com.walletmanager.app.screens.transactions

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

    val dbTransactionList: LiveData<List<Transaction>> =
        transactionRepository.dbTransactionList.asLiveData()

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
                            val afterData = it as TransactionModel.TransactionData
                            afterData.apply {
                                TransactionModel.HeaderData(transaction.month, transaction.year)
                            }
                        }
                    }
                    afterModel == null -> null
                    else -> {
                        val beforeData = beforeModel as TransactionModel.TransactionData
                        val afterData = afterModel as TransactionModel.TransactionData
                        val newMonth = afterData.transaction.month
                        val oldMonth = beforeData.transaction.month
                        when {
                            newMonth != oldMonth -> {
                                TransactionModel.HeaderData(newMonth, afterData.transaction.year)
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

    fun navigatedToMonth(){

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