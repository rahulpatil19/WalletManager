package com.patilapps.walletmanager.screens.update

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import com.patilapps.walletmanager.R
import com.patilapps.walletmanager.database.WalletDatabase
import com.patilapps.walletmanager.database.entities.DbTransaction
import com.patilapps.walletmanager.database.entities.DbYear
import com.patilapps.walletmanager.databinding.ActivityUpdateTransactionBinding
import com.patilapps.walletmanager.domain.Category
import com.patilapps.walletmanager.domain.Transaction
import com.patilapps.walletmanager.screens.create.*
import com.patilapps.walletmanager.utils.formatSelectedDate
import com.patilapps.walletmanager.utils.getMonthString
import java.util.*

class UpdateTransactionActivity : AppCompatActivity(),
    CategoryBottomSheetFragment.CateGoryClickListener,
    SelectDateBottomSheetFragment.DateClickListener {

    private lateinit var binding: ActivityUpdateTransactionBinding

    private lateinit var updateIncomeViewModel: UpdateIncomeViewModel

    private lateinit var updateExpenseViewModel: UpdateExpenseViewModel

    private lateinit var walletDatabase: WalletDatabase


    private var transactionId: Int = 0
    private lateinit var transactionCategory: Category
    private lateinit var transactionType: String
    private lateinit var selectedDate: Date
    private lateinit var selectedMonth: String
    private var selectedYear: Int = 2021
    private lateinit var createdDate: Date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_transaction)

        setSupportActionBar(binding.toolbar)

        walletDatabase = WalletDatabase.getInstance(this)

        // Transaction to edit passed as safe args
        val args: UpdateTransactionActivityArgs by navArgs()
        val transaction = args.transaction

        // initialize transaction fields and update UI
        populateTransactionData(transaction)
    }

    fun populateTransactionData(transaction: Transaction) {
        transactionId = transaction.id
        transactionType = transaction.type
        selectedDate = transaction.date
        selectedMonth = transaction.month
        selectedYear = transaction.year
        createdDate = transaction.createdDate

        transactionCategory =
            Category(transaction.category, transaction.categoryIcon, transaction.categoryColor)

        binding.tvCategoryUpdate.text = transaction.category
        binding.etAmount.setText(transaction.amount.toString())
        binding.etTitle.setText(transaction.title)
        binding.tvDate.setText(formatSelectedDate(transaction.date))
        binding.ivCategoryUpdate.setImageDrawable(ResourcesCompat.getDrawable(resources,
            transaction.categoryIcon, theme))
        binding.ivCategoryUpdate.drawable.setTint(ResourcesCompat.getColor(resources,
            transaction.categoryColor,
            theme))

        setupClickListeners()

        if (transactionType == "income") {
            initIncomeData()
        } else if (transactionType == "expense") {
            initExpenseData()
        }
    }


    private fun setupClickListeners() {
        // Choose category based on transaction type(income or expense)
        binding.layoutSelectCategory.setOnClickListener {
            val categoryBottomSheetFragment =
                CategoryBottomSheetFragment.newInstance(transactionType)
            supportFragmentManager.let {
                categoryBottomSheetFragment.show(it, "category")
            }
        }

        binding.rlChooseDate.setOnClickListener {
            val selectDateBottomSheetFragment = SelectDateBottomSheetFragment()
            supportFragmentManager.let {
                selectDateBottomSheetFragment.show(it, "date")
            }
        }

        binding.buttonSubmit.setOnClickListener {
            val amount = binding.etAmount.text.toString()
            val title = binding.etTitle.text.toString()
            val date = selectedDate
            val type = transactionType

            if (validateForm(title, amount, date)) {
                if (transactionType == "expense") {
                    binding.progressCircular.visibility = View.VISIBLE
                    binding.buttonSubmit.isEnabled = false

                    val dbYear = DbYear(selectedYear)

                    val dbTransaction = DbTransaction(id = transactionId,
                        category = transactionCategory.name,
                        amount = amount.toLong(),
                        title = title,
                        date = date,
                        type = type,
                        month = selectedMonth,
                        year = selectedYear,
                        categoryIcon = transactionCategory.iconId,
                        categoryColor = transactionCategory.colorId,
                        createdDate = createdDate)

                    updateExpenseViewModel.updateTransaction(dbTransaction, dbYear)

                } else if (transactionType == "income") {
                    binding.progressCircular.visibility = View.VISIBLE
                    binding.buttonSubmit.isEnabled = false

                    val dbYear = DbYear(selectedYear)

                    val dbTransaction = DbTransaction(id = transactionId,
                        category = transactionCategory.name,
                        amount = amount.toLong(),
                        title = title,
                        date = date,
                        type = type,
                        month = selectedMonth,
                        year = selectedYear,
                        categoryIcon = transactionCategory.iconId,
                        categoryColor = transactionCategory.colorId,
                        createdDate = createdDate)

                    updateIncomeViewModel.updateIncome(dbTransaction, dbYear)
                }
            }
        }
    }

    private fun initExpenseData() {
        binding.toolbar.title = resources.getString(R.string.title_update_expense)

        val updateExpenseViewModelFactory =
            UpdateExpenseViewModelFactory(application,
                walletDatabase.transactionDao,
                walletDatabase.yearDao
            )

        updateExpenseViewModel = ViewModelProvider(this, updateExpenseViewModelFactory).get(
            UpdateExpenseViewModel::class.java
        )

        updateExpenseViewModel.updateTransaction.observe(this, { addTransaction ->
            if (addTransaction) {
                Toast.makeText(this, "Expense Updated", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        })
    }

    private fun initIncomeData() {
        binding.toolbar.title = resources.getString(R.string.title_update_income)

        val updateIncomeViewModelFactory = UpdateIncomeViewModelFactory(application,
            walletDatabase.transactionDao,
            walletDatabase.yearDao)

        updateIncomeViewModel = ViewModelProvider(this, updateIncomeViewModelFactory).get(
            UpdateIncomeViewModel::class.java
        )

        updateIncomeViewModel.updateTransaction.observe(this, { addTransaction ->
            if (addTransaction) {
                Toast.makeText(this, "Income Updated", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        })
    }


    private fun validateForm(title: String, amount: String, date: Date): Boolean {
        return when {
            title.isEmpty() -> {
                Snackbar.make(binding.root, "Please enter title", Snackbar.LENGTH_SHORT).show()
                false
            }
            amount.isEmpty() -> {
                Snackbar.make(binding.root, "Please enter amount", Snackbar.LENGTH_SHORT).show()
                false
            }
            date.toString().isEmpty() -> {
                Snackbar.make(binding.root, "Select date", Snackbar.LENGTH_SHORT).show()
                false
            }
            else -> {
                true
            }
        }
    }

    override fun onCategoryClick(category: Category) {
        transactionCategory = category
        binding.tvCategoryUpdate.text = category.name
        binding.ivCategoryUpdate.setImageDrawable(ResourcesCompat.getDrawable(resources,
            category.iconId,
            resources.newTheme()))
        //  Toast.makeText(this, "Category selected is ${category.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onDateSelected(calendar: Calendar) {
        selectedDate = Date(calendar.timeInMillis)
        selectedMonth = getMonthString(calendar.get(Calendar.MONTH))
        selectedYear = calendar.get(Calendar.YEAR)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val sDay: String
        if (day < 10) {
            sDay = "0$day"
        } else {
            sDay = "$day"
        }

        binding.tvDate.text = getString(R.string.selected_date, sDay, selectedMonth, selectedYear)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}