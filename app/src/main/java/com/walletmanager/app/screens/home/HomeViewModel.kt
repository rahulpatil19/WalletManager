package com.walletmanager.app.screens.home

import androidx.lifecycle.*
import com.walletmanager.app.database.dao.TransactionDao
import com.walletmanager.app.database.entities.asTransaction
import com.walletmanager.app.domain.Transaction
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.single

class HomeViewModel(val transactionDao: TransactionDao, homeRepository: HomeRepository) : ViewModel() {

    val recentlyAddedList: LiveData<List<Transaction>> = homeRepository.dbTransactionList.asLiveData()

    private var _totalAmount = MutableLiveData<Long>()
    val totalAmount : LiveData<Long>
    get() = _totalAmount

    private var _totalIncome = MutableLiveData<Long>()
    val totalIncome: LiveData<Long>
    get() = _totalIncome

    private var _totalExpense = MutableLiveData<Long>()
    val totalExpense: LiveData<Long>
    get() = _totalExpense

    init {
       // calculateAmount()
        //getRecentlyAddedTransaction()
        /*viewModelScope{
            transactionDao.getAllTransaction().collect { dbTransactionList->
                _recentlyAddedList.value = dbTransactionList.asTransaction()
            }
        }*/
    }

    fun calculateAmount(){
        _totalAmount.value = 0
        _totalIncome.value = 0
        _totalExpense.value = 0
        val list = recentlyAddedList.value
        if (list != null) {
            for (i in list){
                _totalAmount.value = _totalAmount.value?.plus(i.amount)
                if (i.type.equals("income")){
                    _totalIncome.value = _totalIncome.value?.plus(i.amount)
                }
                else if (i.type.equals("expense")){
                    _totalExpense.value = _totalExpense.value?.plus(i.amount)
                }
            }
        }
    }

}