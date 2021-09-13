package com.patilapps.walletmanager.screens.monthlydata

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.patilapps.walletmanager.database.dao.TransactionDao
import com.patilapps.walletmanager.database.entities.DbTransaction
import com.patilapps.walletmanager.database.entities.asTransaction
import com.patilapps.walletmanager.domain.Transaction
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MonthDataViewModel(
    val month: String,
    val year: Int,
    val totalAmount: Long,
    val incomeAmount: Long,
    val expenseAmount: Long,
    val transactionDao: TransactionDao,
    application: Application,
) : AndroidViewModel(application) {

    var selectedTransaction = MutableLiveData<DbTransaction>()

    private var _totalAmount = MutableLiveData<Long>()
    val totalAmountLiveData: LiveData<Long>
        get() = _totalAmount

    private var _incomeAmount = MutableLiveData<Long>()
    val incomeAmountLiveData: LiveData<Long>
        get() = _incomeAmount

    private var _expenseAmount = MutableLiveData<Long>()
    val expenseAmountLiveData: LiveData<Long>
        get() = _expenseAmount

    var transactionList: LiveData<List<Transaction>> =
        transactionDao.getTransactionByMonth(month, year).map {
            it.asTransaction()
        }.asLiveData()

    init {
        _totalAmount.value = totalAmount
        _incomeAmount.value = incomeAmount
        _expenseAmount.value = expenseAmount
        Log.d("Month Viewmodel", "initialized")
    }

    fun updateAmountValues(dbTransaction: DbTransaction) {
      /*  viewModelScope.launch {
            selectedTransaction =
                transactionDao.getTransactionById(dbTransaction.id) as MutableLiveData<DbTransaction>
            val newAmount = selectedTransaction.value?.amount

            val amountWithoutUpdatedTransaction = _totalAmount.value?.minus(dbTransaction.amount)
            _totalAmount.value = amountWithoutUpdatedTransaction?.plus(newAmount!!)

            val type =  selectedTransaction.value?.type

            if (type == "income") {
                val incomeWithoutUpdateTransaction =
                    _incomeAmount.value?.minus(dbTransaction.amount)
                _incomeAmount.value = incomeWithoutUpdateTransaction?.plus(newAmount!!)
            } else if (type == "expense") {
                val expenseWithoutUpdateTransaction =
                    _incomeAmount.value?.minus(dbTransaction.amount)
                _expenseAmount.value = expenseWithoutUpdateTransaction?.plus(newAmount!!)
            }
        }*/
    }

}

class MonthDataViewModelFactory(
    val month: String,
    val year: Int,
    val totalAmount: Long,
    val incomeAmount: Long,
    val expenseAmount: Long,
    val transactionDao: TransactionDao,
    val application: Application,
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MonthDataViewModel::class.java)) {
            return MonthDataViewModel(month,
                year,
                totalAmount,
                incomeAmount,
                expenseAmount,
                transactionDao,
                application) as T
        }
        throw IllegalArgumentException("No Viewmodel found")
    }
}
