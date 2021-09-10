package com.patilapps.walletmanager.screens.statistic

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.github.mikephil.charting.data.PieEntry
import com.patilapps.walletmanager.database.dao.TransactionDao
import com.patilapps.walletmanager.database.dao.YearDao
import com.patilapps.walletmanager.domain.Statistics
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class StatisticViewModel(val transactionDao: TransactionDao, val yearDao: YearDao) : ViewModel() {

    private var _statisticsList = MutableLiveData<List<Statistics>>()
    val statisticsList: LiveData<List<Statistics>>
        get() = _statisticsList

    private var _entryList = MutableLiveData<List<PieEntry>>()
    val entryList: LiveData<List<PieEntry>>
        get() = _entryList

    private var _currentYear = MutableLiveData<Int>()
    val currentYear: LiveData<Int>
        get() = _currentYear

    var totalIncome: Double = 0.0
    var totalExpense: Double = 0.0

    var janTotalTransaction: Int = 0
    var janTotalAmount: Long = 0
    var janExpense: Long = 0
    var janIncome: Long = 0
    var janLastModified: String = ""

    var febTotalTransaction: Int = 0
    var febTotalAmount: Long = 0
    var febExpense: Long = 0
    var febIncome: Long = 0
    var febLastModified: String = ""

    var marTotalTransaction: Int = 0
    var marTotalAmount: Long = 0
    var marExpense: Long = 0
    var marIncome: Long = 0
    var marLastModified: String = ""

    var aprTotalTransaction: Int = 0
    var aprTotalAmount: Long = 0
    var aprExpense: Long = 0
    var aprIncome: Long = 0
    var aprLastModified: String = ""

    var mayTotalTransaction: Int = 0
    var mayTotalAmount: Long = 0
    var mayExpense: Long = 0
    var mayIncome: Long = 0
    var mayLastModified: String = ""

    var junTotalTransaction: Int = 0
    var junTotalAmount: Long = 0
    var junExpense: Long = 0
    var junIncome: Long = 0
    var junLastModified: String = ""

    var julTotalTransaction: Int = 0
    var julTotalAmount: Long = 0
    var julExpense: Long = 0
    var julIncome: Long = 0
    var julLastModified: String = ""

    var augTotalTransaction: Int = 0
    var augTotalAmount: Long = 0
    var augExpense: Long = 0
    var augIncome: Long = 0
    var augLastModified: String = ""

    var sepTotalTransaction: Int = 0
    var sepTotalAmount: Long = 0
    var sepExpense: Long = 0
    var sepIncome: Long = 0
    var sepLastModified: String = ""

    var octTotalTransaction: Int = 0
    var octTotalAmount: Long = 0
    var octExpense: Long = 0
    var octIncome: Long = 0
    var octLastModified: String = ""

    var novTotalTransaction: Int = 0
    var novTotalAmount: Long = 0
    var novExpense: Long = 0
    var novIncome: Long = 0
    var novLastModified: String = ""

    var decTotalTransaction: Int = 0
    var decTotalAmount: Long = 0
    var decExpense: Long = 0
    var decIncome: Long = 0
    var decLastModified: String = ""

    init {
        _currentYear.value = 2021
        resetValues()
    }

    // cd Users\SCEA\AppData\Local\Android\sdk

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
                                janTotalTransaction = janTotalTransaction.plus(1)
                                janLastModified = transaction.date.toString()
                                janTotalAmount = janTotalAmount.plus(transaction.amount)
                                Log.d("Jan TotalAmount:", janTotalAmount.toString())
                            }
                            "Feb" -> {
                                if (transaction.type == "income") {
                                    febIncome = febIncome.plus(transaction.amount)
                                } else if (transaction.type == "expense") {
                                    febExpense = febExpense.plus(transaction.amount)
                                }
                                febTotalTransaction = febTotalTransaction.plus(1)
                                febLastModified = transaction.date.toString()
                                febTotalAmount = febTotalAmount.plus(transaction.amount)
                            }

                            "Mar" -> {
                                if (transaction.type == "income") {
                                    marIncome = marIncome.plus(transaction.amount)
                                } else if (transaction.type == "expense") {
                                    marExpense = marExpense.plus(transaction.amount)
                                }
                                marTotalTransaction = marTotalTransaction.plus(1)
                                marLastModified = transaction.date.toString()
                                marTotalAmount = marTotalAmount.plus(transaction.amount)
                            }

                            "Apr" -> {
                                if (transaction.type == "income") {
                                    aprIncome = aprIncome.plus(transaction.amount)
                                } else if (transaction.type == "expense") {
                                    aprExpense = aprExpense.plus(transaction.amount)
                                }
                                aprTotalTransaction = aprTotalTransaction.plus(1)
                                aprLastModified = transaction.date.toString()
                                aprTotalAmount = aprTotalAmount.plus(transaction.amount)
                            }

                            "May" -> {
                                if (transaction.type == "income") {
                                    mayIncome = mayIncome.plus(transaction.amount)
                                } else if (transaction.type == "expense") {
                                    mayExpense = mayExpense.plus(transaction.amount)
                                }
                                mayTotalTransaction = mayTotalTransaction.plus(1)
                                mayLastModified = transaction.date.toString()
                                mayTotalAmount = mayTotalAmount.plus(transaction.amount)
                            }

                            "Jun" -> {
                                if (transaction.type == "income") {
                                    junIncome = junIncome.plus(transaction.amount)
                                } else if (transaction.type == "expense") {
                                    junExpense = junExpense.plus(transaction.amount)
                                }
                                junTotalTransaction = junTotalTransaction.plus(1)
                                junLastModified = transaction.date.toString()
                                junTotalAmount = junTotalAmount.plus(transaction.amount)
                            }

                            "Jul" -> {
                                if (transaction.type == "income") {
                                    julIncome = julIncome.plus(transaction.amount)
                                } else if (transaction.type == "expense") {
                                    julExpense = julExpense.plus(transaction.amount)
                                }
                                julTotalTransaction = julTotalTransaction.plus(1)
                                julLastModified = transaction.date.toString()
                                julTotalAmount = junTotalAmount.plus(transaction.amount)
                            }

                            "Aug" -> {
                                if (transaction.type == "income") {
                                    augIncome = augIncome.plus(transaction.amount)
                                } else if (transaction.type == "expense") {
                                    augExpense = augExpense.plus(transaction.amount)
                                }
                                augTotalTransaction = augTotalTransaction.plus(1)
                                augLastModified = transaction.date.toString()
                                augTotalAmount = augTotalAmount.plus(transaction.amount)
                            }

                            "Sep" -> {
                                if (transaction.type == "income") {
                                    sepIncome = sepIncome.plus(transaction.amount)
                                } else if (transaction.type == "expense") {
                                    sepExpense = sepExpense.plus(transaction.amount)
                                }
                                sepTotalTransaction = sepTotalTransaction.plus(1)
                                sepLastModified = transaction.date.toString()
                                sepTotalAmount = sepTotalAmount.plus(transaction.amount)
                            }

                            "Oct" -> {
                                if (transaction.type == "income") {
                                    octIncome = octIncome.plus(transaction.amount)
                                } else if (transaction.type == "expense") {
                                    octExpense = octExpense.plus(transaction.amount)
                                }
                                octTotalTransaction = octTotalTransaction.plus(1)
                                octLastModified = transaction.date.toString()
                                octTotalAmount = octTotalAmount.plus(transaction.amount)
                            }

                            "Nov" -> {
                                if (transaction.type == "income") {
                                    novIncome = novIncome.plus(transaction.amount)
                                } else if (transaction.type == "expense") {
                                    novExpense = novExpense.plus(transaction.amount)
                                }
                                novTotalTransaction = novTotalTransaction.plus(1)
                                novLastModified = transaction.date.toString()
                                novTotalAmount = novTotalAmount.plus(transaction.amount)

                            }

                            "Dec" -> {
                                if (transaction.type == "income") {
                                    decIncome = decIncome.plus(transaction.amount)
                                } else if (transaction.type == "expense") {
                                    decExpense = decExpense.plus(transaction.amount)
                                }
                                decTotalTransaction = decTotalTransaction.plus(1)
                                decLastModified = transaction.date.toString()
                                decTotalAmount = decTotalAmount.plus(transaction.amount)
                            }
                        }
                    }

                    createStatisticsList()

                    val totalAmount = totalIncome.plus(totalExpense)

                    if (totalAmount > 0) {
                        val incomePercent = totalIncome.times(100).div(totalAmount)
                        val expensePercent = totalExpense.times(100).div(totalAmount)

                        // PieChart entries
                        val incomeEntry = PieEntry(incomePercent.toFloat(), "Income")
                        val expenseEntry = PieEntry(expensePercent.toFloat(), "Expense")
                        val entryList = listOf(incomeEntry, expenseEntry)

                        _entryList.value = entryList
                    } else {
                        _statisticsList.value = listOf()
                        _entryList.value = listOf()
                    }
                }
            }
        }
    }

    fun createStatisticsList() {
        val list = mutableListOf<Statistics>()
        if (janTotalTransaction > 0) {
            list.add(0,
                Statistics(
                    "January",
                    janTotalAmount,
                    janIncome,
                    janExpense,
                    janTotalTransaction,
                    janLastModified
                ))
        }

        if (febTotalTransaction > 0) {
            list.add(0,
                Statistics(
                    "February",
                    febTotalAmount,
                    febIncome,
                    febExpense,
                    febTotalTransaction,
                    febLastModified
                ))
        }

        if (marTotalTransaction > 0) {
            list.add(0,
                Statistics(
                    "March",
                    marTotalAmount,
                    marIncome,
                    marExpense,
                    marTotalTransaction,
                    marLastModified
                ))
        }

        if (aprTotalTransaction > 0) {
            list.add(0,
                Statistics(
                    "April",
                    aprTotalAmount,
                    aprIncome,
                    aprExpense,
                    aprTotalTransaction,
                    aprLastModified
                ))
        }
        if (mayTotalTransaction > 0) {
            list.add(0,
                Statistics(
                    "May",
                    mayTotalAmount,
                    mayIncome,
                    mayExpense,
                    mayTotalTransaction,
                    mayLastModified
                ))
        }
        if (junTotalTransaction > 0) {
            list.add(0,
                Statistics(
                    "June",
                    junTotalAmount,
                    junIncome,
                    junExpense,
                    junTotalTransaction,
                    junLastModified
                ))
        }
        if (julTotalTransaction > 0) {
            list.add(0,
                Statistics(
                    "July",
                    julTotalAmount,
                    julIncome,
                    julExpense,
                    julTotalTransaction,
                    julLastModified
                ))
        }
        if (augTotalTransaction > 0) {
            list.add(0,
                Statistics(
                    "August",
                    augTotalAmount,
                    augIncome,
                    augExpense,
                    augTotalTransaction,
                    augLastModified
                ))
        }
        if (sepTotalTransaction > 0) {
            list.add(0,
                Statistics(
                    "September",
                    sepTotalAmount,
                    sepIncome,
                    sepExpense,
                    sepTotalTransaction,
                    sepLastModified
                ))
        }
        if (octTotalTransaction > 0) {
            list.add(0,
                Statistics(
                    "October",
                    octTotalAmount,
                    octIncome,
                    octExpense,
                    octTotalTransaction,
                    octLastModified
                ))
        }
        if (novTotalTransaction > 0) {
            list.add(0,
                Statistics(
                    "November",
                    novTotalAmount,
                    novIncome,
                    novExpense,
                    novTotalTransaction,
                    novLastModified
                ))
        }

        _statisticsList.value = list

    }

    private fun resetValues() {
        janTotalTransaction = 0
        febTotalTransaction = 0
        marTotalTransaction = 0
        aprTotalTransaction = 0
        mayTotalTransaction = 0
        junTotalTransaction = 0
        julTotalTransaction = 0
        augTotalTransaction = 0
        sepTotalTransaction = 0
        octTotalTransaction = 0
        novTotalTransaction = 0
        decTotalTransaction = 0

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