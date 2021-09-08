package com.patilapps.walletmanager.screens.settings

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patilapps.walletmanager.database.WalletDatabase
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class SettingsViewModel(application: Application, val walletDatabase: WalletDatabase): ViewModel() {


    init {

    }

    fun deleteAll(){
        viewModelScope.launch {
            walletDatabase.transactionDao.deleteAllTransactions()
            walletDatabase.incomeDao.deleteAllIncomes()
            walletDatabase.expenseDao.deleteAllExpenses()
            walletDatabase.monthDao.deleteAllMonths()
            walletDatabase.yearDao.deleteAllYears()
        }
    }
}

class SettingsViewModelFactory(val application: Application, val walletDatabase: WalletDatabase) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)){
            return SettingsViewModel(application, walletDatabase) as T
        }
        throw IllegalArgumentException("Viewmodel not found")
    }
}