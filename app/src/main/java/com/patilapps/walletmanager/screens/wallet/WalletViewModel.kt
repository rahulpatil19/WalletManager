package com.patilapps.walletmanager.screens.wallet

import androidx.lifecycle.*
import com.patilapps.walletmanager.database.WalletDatabase
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class WalletViewModel(val walletDatabase: WalletDatabase) : ViewModel() {

    private var _pagerList = MutableLiveData<List<String>>()

    private var _filterType = MutableLiveData<String>()
    val filterType: LiveData<String>
        get() = _filterType

    val pagerList: LiveData<List<String>>
        get() = _pagerList

    fun filterOptionSelected(filterType: String) {
        if (filterType.equals("year")) {
            viewModelScope.launch {
                _filterType.value = filterType
              //  _pagerList.value = walletDatabase.yearDao.getAllYears().asFilterList()
            }
        } else if (filterType.equals("month")) {
            viewModelScope.launch {
                _filterType.value = filterType
              //  _pagerList.value = walletDatabase.monthDao.getAllMonthWithYear().value?.asFilterList()
            }
        }
    }

}


class WalletViewModelFactory(private val walletDatabase: WalletDatabase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WalletViewModel::class.java)) {
            return WalletViewModel(walletDatabase) as T
        } else {
            throw IllegalArgumentException("Viewmodel not found")
        }
    }


}