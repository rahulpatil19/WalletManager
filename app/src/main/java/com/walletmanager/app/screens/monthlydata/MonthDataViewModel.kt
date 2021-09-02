package com.walletmanager.app.screens.monthlydata

import android.app.Application
import androidx.lifecycle.*
import com.walletmanager.app.database.dao.TransactionDao
import com.walletmanager.app.database.entities.asTransaction
import com.walletmanager.app.domain.Transaction
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MonthDataViewModel(
    val month: String,
    val year: Int,
    val transactionDao: TransactionDao,
    application: Application,
) : AndroidViewModel(application) {

    var transactionList: LiveData<List<Transaction>> =
        transactionDao.getTransactionByMonth(month, year).map {
            it.asTransaction()
        }.asLiveData()

}

class MonthDataViewModelFactory(
    val month: String,
    val year: Int,
    val transactionDao: TransactionDao,
    val application: Application,
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MonthDataViewModel::class.java)) {
            return MonthDataViewModel(month, year, transactionDao, application) as T
        }
        throw IllegalArgumentException("No Viewmodel found")
    }
}
