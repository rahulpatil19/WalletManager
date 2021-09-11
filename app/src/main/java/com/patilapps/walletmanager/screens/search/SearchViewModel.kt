package com.patilapps.walletmanager.screens.search

import android.app.Application
import android.util.Log
import android.widget.SearchView
import androidx.lifecycle.*
import com.patilapps.walletmanager.database.dao.TransactionDao
import com.patilapps.walletmanager.database.entities.asTransaction
import com.patilapps.walletmanager.domain.Statistics
import com.patilapps.walletmanager.domain.Transaction
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.IllegalArgumentException

class SearchViewModel(
    application: Application,
    val transactionDao: TransactionDao,
) :
    AndroidViewModel(application) {

    private var _transactionList = MutableLiveData<List<Transaction>>()
    val transactionList: LiveData<List<Transaction>>
        get() = _transactionList

    var result = MutableLiveData<SearchResult>()

    init {
        result.value = SearchResult.Error("Start")
    }

    fun getTransactionByKeyword(keyword: String) {
        viewModelScope.launch {
            result.value = SearchResult.Loading
            transactionDao.getTransactionByKeyword(keyword).collect {
                _transactionList.value = it.asTransaction()
                if (it.isNotEmpty()) {
                    result.value = SearchResult.Success("Success")
                } else {
                    result.value = SearchResult.Error("Empty")
                }
            }
        }
    }

    fun clearList() {
        _transactionList.value = listOf()
        result.value = SearchResult.Error("List is empty")
    }
}

sealed class SearchResult {
    class Success(val message: String) : SearchResult()

    object Loading : SearchResult()

    class Error(val message: String) : SearchResult()
}

class SearchViewModelFactory(val application: Application, val transactionDao: TransactionDao) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(application, transactionDao) as T
        }
        throw IllegalArgumentException("Viewmodel not found")
    }
}