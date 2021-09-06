package com.walletmanager.app.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.walletmanager.app.database.dao.TransactionDao
import java.lang.IllegalArgumentException

class HomeViewModelFactory(
    private val transactionDao: TransactionDao,
    private val homeRepository: HomeRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(transactionDao, homeRepository) as T
        }
        throw IllegalArgumentException("No view model found")
    }
}