package com.walletmanager.app.screens.statistic

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieEntry
import com.walletmanager.app.database.dao.TransactionDao
import com.walletmanager.app.database.dao.YearDao
import com.walletmanager.app.database.dao.asFilterList
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

class StatisticViewModel(val transactionDao: TransactionDao, val yearDao: YearDao) : ViewModel() {


    var totalIncome: Double = 0.0
    var totalExpense: Double = 0.0

    private var _entryList = MutableLiveData<List<PieEntry>>()
    val entryList: LiveData<List<PieEntry>>
        get() = _entryList

    private var _currentYear = MutableLiveData<Int>()
    val currentYear: LiveData<Int>
        get() = _currentYear

    private var _janTotalTransaction = MutableLiveData<Int>()
    val janTotalTransaction: LiveData<Int>
        get() = _janTotalTransaction

    var janTotalAmount: Long = 0
    var janExpense: Long = 0
    var janIncome: Long = 0
    var janLastModified: String = ""

    private var _febTotalTransaction = MutableLiveData<Int>()
    val febTotalTransaction: LiveData<Int>
        get() = _febTotalTransaction

    var febTotalAmount: Long = 0
    var febExpense: Long = 0
    var febIncome: Long = 0
    var febLastModified: String = ""

    private var _marTotalTransaction = MutableLiveData<Int>()
    val marTotalTransaction: LiveData<Int>
        get() = _marTotalTransaction

    var marTotalAmount: Long = 0
    var marExpense: Long = 0
    var marIncome: Long = 0
    var marLastModified: String = ""

    private var _aprTotalTransaction = MutableLiveData<Int>()
    val aprTotalTransaction: LiveData<Int>
        get() = _aprTotalTransaction

    var aprTotalAmount: Long = 0
    var aprExpense: Long = 0
    var aprIncome: Long = 0
    var aprLastModified: String = ""

    private var _mayTotalTransaction = MutableLiveData<Int>()
    val mayTotalTransaction: LiveData<Int>
        get() = _mayTotalTransaction

    var mayTotalAmount: Long = 0
    var mayExpense: Long = 0
    var mayIncome: Long = 0
    var mayLastModified: String = ""

    private var _junTotalTransaction = MutableLiveData<Int>()
    val junTotalTransaction: LiveData<Int>
        get() = _junTotalTransaction

    var junTotalAmount: Long = 0
    var junExpense: Long = 0
    var junIncome: Long = 0
    var junLastModified: String = ""


    private var _julTotalTransaction = MutableLiveData<Int>()
    val julTotalTransaction: LiveData<Int>
        get() = _julTotalTransaction

    var julTotalAmount: Long = 0
    var julExpense: Long = 0
    var julIncome: Long = 0
    var julLastModified: String = ""


    private var _augTotalTransaction = MutableLiveData<Int>()
    val augTotalTransaction: LiveData<Int>
        get() = _augTotalTransaction

    var augTotalAmount: Long = 0
    var augExpense: Long = 0
    var augIncome: Long = 0
    var augLastModified: String = ""


    private var _sepTotalTransaction = MutableLiveData<Int>()
    val sepTotalTransaction: LiveData<Int>
        get() = _sepTotalTransaction

    var sepTotalAmount: Long = 0
    var sepExpense: Long = 0
    var sepIncome: Long = 0
    var sepLastModified: String = ""

    private var _octTotalTransaction = MutableLiveData<Int>()
    val octTotalTransaction: LiveData<Int>
        get() = _octTotalTransaction

    var octTotalAmount: Long = 0
    var octExpense: Long = 0
    var octIncome: Long = 0
    var octLastModified: String = ""

    private var _novTotalTransaction = MutableLiveData<Int>()
    val novTotalTransaction: LiveData<Int>
        get() = _novTotalTransaction

    var novTotalAmount: Long = 0
    var novExpense: Long = 0
    var novIncome: Long = 0
    var novLastModified: String = ""

    private var _decTotalTransaction = MutableLiveData<Int>()
    val decTotalTransaction: LiveData<Int>
        get() = _decTotalTransaction

    var decTotalAmount: Long = 0
    var decExpense: Long = 0
    var decIncome: Long = 0
    var decLastModified: String = ""


    init {
        _currentYear.value = 2021
        resetValues()
    }

    fun getAllTransactionFromYear(year: Int) {
        resetValues()
        viewModelScope.launch {
            transactionDao.getTransactionByYear(year).collect {
                Log.d("Transaciton List:", it.toString())
                Log.d("Selected year", year.toString())
                it.let {
                    for (transaction in it) {
                        if (transaction.type == "income") {
                            totalIncome = totalIncome.plus(transaction.amount)
                        } else if (transaction.type == "expense") {
                            totalExpense = totalExpense.plus(transaction.amount)
                        }

                        when (transaction.month) {
                            "Jan" -> {
                                if (transaction.type == "income") {
                                    janIncome = janIncome.plus(transaction.amount)
                                } else if (transaction.type == "expense") {
                                    janExpense = janExpense.plus(transaction.amount)
                                }
                                janLastModified = transaction.date.toString()
                                janTotalAmount = janTotalAmount.plus(transaction.amount)
                                _janTotalTransaction.value = _janTotalTransaction.value?.plus(1)
                                Log.d("Jan TotalAmount:", janTotalAmount.toString())
                            }
                            "Feb" -> {
                                if (transaction.type == "income") {
                                    febIncome = febIncome.plus(transaction.amount)
                                } else if (transaction.type == "expense") {
                                    febExpense = febExpense.plus(transaction.amount)
                                }
                                febLastModified = transaction.date.toString()
                                febTotalAmount = febTotalAmount.plus(transaction.amount)
                                _febTotalTransaction.value = _febTotalTransaction.value?.plus(1)
                            }

                            "Mar" -> {
                                if (transaction.type == "income") {
                                    marIncome = marIncome.plus(transaction.amount)
                                } else if (transaction.type == "expense") {
                                    marExpense = marExpense.plus(transaction.amount)
                                }
                                marLastModified = transaction.date.toString()
                                marTotalAmount = marTotalAmount.plus(transaction.amount)
                                _marTotalTransaction.value = _marTotalTransaction.value?.plus(1)

                            }

                            "Apr" -> {
                                if (transaction.type == "income") {
                                    aprIncome = aprIncome.plus(transaction.amount)
                                } else if (transaction.type == "expense") {
                                    aprExpense = aprExpense.plus(transaction.amount)
                                }
                                aprLastModified = transaction.date.toString()
                                aprTotalAmount = aprTotalAmount.plus(transaction.amount)
                                _aprTotalTransaction.value = _aprTotalTransaction.value?.plus(1)

                            }

                            "May" -> {
                                if (transaction.type == "income") {
                                    mayIncome = mayIncome.plus(transaction.amount)
                                } else if (transaction.type == "expense") {
                                    mayExpense = mayExpense.plus(transaction.amount)
                                }
                                mayLastModified = transaction.date.toString()
                                mayTotalAmount = mayTotalAmount.plus(transaction.amount)
                                _mayTotalTransaction.value = _mayTotalTransaction.value?.plus(1)

                            }

                            "Jun" -> {
                                if (transaction.type == "income") {
                                    junIncome = junIncome.plus(transaction.amount)
                                } else if (transaction.type == "expense") {
                                    junExpense = junExpense.plus(transaction.amount)
                                }
                                junLastModified = transaction.date.toString()
                                junTotalAmount = junTotalAmount.plus(transaction.amount)
                                _junTotalTransaction.value = _junTotalTransaction.value?.plus(1)

                            }

                            "Jul" -> {
                                if (transaction.type == "income") {
                                    julIncome = julIncome.plus(transaction.amount)
                                } else if (transaction.type == "expense") {
                                    julExpense = julExpense.plus(transaction.amount)
                                }
                                julLastModified = transaction.date.toString()
                                julTotalAmount = junTotalAmount.plus(transaction.amount)
                                _julTotalTransaction.value = _julTotalTransaction.value?.plus(1)

                            }

                            "Aug" -> {
                                if (transaction.type == "income") {
                                    augIncome = augIncome.plus(transaction.amount)
                                } else if (transaction.type == "expense") {
                                    augExpense = augExpense.plus(transaction.amount)
                                }
                                augLastModified = transaction.date.toString()
                                augTotalAmount = augTotalAmount.plus(transaction.amount)
                                _augTotalTransaction.value = _augTotalTransaction.value?.plus(1)

                            }

                            "Sep" -> {
                                if (transaction.type == "income") {
                                    sepIncome = sepIncome.plus(transaction.amount)
                                } else if (transaction.type == "expense") {
                                    sepExpense = sepExpense.plus(transaction.amount)
                                }
                                sepLastModified = transaction.date.toString()
                                sepTotalAmount = sepTotalAmount.plus(transaction.amount)
                                _sepTotalTransaction.value = _sepTotalTransaction.value?.plus(1)

                            }

                            "Oct" -> {
                                if (transaction.type == "income") {
                                    octIncome = octIncome.plus(transaction.amount)
                                } else if (transaction.type == "expense") {
                                    octExpense = octExpense.plus(transaction.amount)
                                }
                                octLastModified = transaction.date.toString()
                                octTotalAmount = octTotalAmount.plus(transaction.amount)
                                _octTotalTransaction.value = _octTotalTransaction.value?.plus(1)

                            }

                            "Nov" -> {
                                if (transaction.type == "income") {
                                    novIncome = novIncome.plus(transaction.amount)
                                } else if (transaction.type == "expense") {
                                    novExpense = novExpense.plus(transaction.amount)
                                }
                                novLastModified = transaction.date.toString()
                                novTotalAmount = novTotalAmount.plus(transaction.amount)
                                _novTotalTransaction.value = _novTotalTransaction.value?.plus(1)

                            }

                            "Dec" -> {
                                if (transaction.type == "income") {
                                    decIncome = decIncome.plus(transaction.amount)
                                } else if (transaction.type == "expense") {
                                    decExpense = decExpense.plus(transaction.amount)
                                }
                                decLastModified = transaction.date.toString()
                                decTotalAmount = decTotalAmount.plus(transaction.amount)
                                _decTotalTransaction.value = _decTotalTransaction.value?.plus(1)
                            }
                        }
                    }

                    val totalAmount = totalIncome.plus(totalExpense)
                    Log.d("Total amount", totalAmount.toString())

                    if (totalAmount > 0){
                        val incomePercent  = totalIncome.times(100).div(totalAmount)
                        val expensePercent  = totalExpense.times(100).div(totalAmount)

                        Log.d("Total Income", totalIncome.toString())
                        Log.d("Total Expense", totalExpense.toString())

                        Log.d("Income Percent", incomePercent.toString())
                        Log.d("Expense Percent", expensePercent.toString())

                        // PieChart entries
                        val incomeEntry = PieEntry(incomePercent.toFloat(), "Income")
                        val expenseEntry = PieEntry(expensePercent.toFloat(), "Expense")
                        val entryList = listOf(incomeEntry, expenseEntry)

                        Log.d("Entry list", entryList.toString())

                        _entryList.value = entryList
                    }
                }
            }
        }

    }

    fun resetValues() {
        _janTotalTransaction.value = 0
        _febTotalTransaction.value = 0
        _marTotalTransaction.value = 0
        _aprTotalTransaction.value = 0
        _mayTotalTransaction.value = 0
        _junTotalTransaction.value = 0
        _julTotalTransaction.value = 0
        _augTotalTransaction.value = 0
        _sepTotalTransaction.value = 0
        _octTotalTransaction.value = 0
        _novTotalTransaction.value = 0
        _decTotalTransaction.value = 0

        totalIncome = 0.0
        totalExpense = 0.0

        janTotalAmount = 0
        janExpense = 0
        janIncome = 0
        janLastModified = ""

        febTotalAmount = 0
        febExpense = 0
        febIncome = 0
        febLastModified = ""

        marTotalAmount = 0
        marExpense = 0
        marIncome = 0
        marLastModified = ""

        aprTotalAmount = 0
        aprExpense = 0
        aprIncome = 0
        aprLastModified = ""

        mayTotalAmount = 0
        mayExpense = 0
        mayIncome = 0
        mayLastModified = ""

        junTotalAmount = 0
        junExpense = 0
        junIncome = 0
        junLastModified = ""

        julTotalAmount = 0
        julExpense = 0
        julIncome = 0
        julLastModified = ""

        augTotalAmount = 0
        augExpense = 0
        augIncome = 0
        augLastModified = ""

        sepTotalAmount = 0
        sepExpense = 0
        sepIncome = 0
        sepLastModified = ""

        octTotalAmount = 0
        octExpense = 0
        octIncome = 0
        octLastModified = ""

        novTotalAmount = 0
        novExpense = 0
        novIncome = 0
        novLastModified = ""

        decTotalAmount = 0
        decExpense = 0
        decIncome = 0
        decLastModified = ""
    }

    fun onPreviousYearClick() {
        _currentYear.value = _currentYear.value?.minus(1)
    }

    fun onNextYearClick() {
        _currentYear.value = _currentYear.value?.plus(1)
    }

}


class StatisticViewModelFactory(
    val application: Application,
    private val transactionDao: TransactionDao,
    private val yearDao: YearDao,
) :
    ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatisticViewModel::class.java)) {
            return StatisticViewModel(transactionDao, yearDao) as T
        }
        throw IllegalArgumentException("No viewmodel found")
    }
}