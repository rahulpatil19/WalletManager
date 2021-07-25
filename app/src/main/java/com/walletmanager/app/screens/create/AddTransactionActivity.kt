package com.walletmanager.app.screens.create

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.walletmanager.app.R
import com.walletmanager.app.database.WalletDatabase
import com.walletmanager.app.database.entities.DbExpense
import com.walletmanager.app.database.entities.DbIncome
import com.walletmanager.app.database.entities.DbTransaction
import com.walletmanager.app.databinding.ActivityAddTransactionBinding
import com.walletmanager.app.domain.Category
import com.walletmanager.app.utils.getCurrentDate
import com.walletmanager.app.utils.getMonthString
import java.util.*

class AddTransactionActivity : AppCompatActivity(),
    CategoryBottomSheetFragment.CateGoryClickListener,
    SelectDateBottomSheetFragment.DateClickListener {

    private lateinit var transactionType: String

    private lateinit var binding: ActivityAddTransactionBinding

    private lateinit var addIncomeViewModel: AddIncomeViewModel
    private lateinit var addIncomeViewModelFactory: AddIncomeViewModelFactory

    private lateinit var addExpenseViewModel: AddExpenseViewModel

    private lateinit var walletDatabase: WalletDatabase

    private var transactionCategory: Category = Category(1, "Other", R.drawable.ic_other)

    private lateinit var selectedDate: Date
    private lateinit var selectedMonth: String
    private var selectedYear: Int = 2021

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_transaction)

        walletDatabase = WalletDatabase.getInstance(this)

        val bundle = intent.extras

        bundle?.let {
            val args = AddTransactionActivityArgs.fromBundle(bundle)
            transactionType = args.transactionType

            val date = getCurrentDate()
            binding.tvDate.text = date

            setupClickListeners()

            if (transactionType == "income") {
                initIncomeData()
            } else if (transactionType == "expense") {
                initExpenseData()
            }
        }
    }

    private fun initExpenseData() {
        binding.toolbar.title = resources.getString(R.string.title_add_expense)
        val addExpenseViewModelFactory =
            AddExpenseViewModelFactory(walletDatabase.expenseDao,
                walletDatabase.transactionDao,
                application)
        addExpenseViewModel = ViewModelProvider(this, addExpenseViewModelFactory).get(
            AddExpenseViewModel::class.java
        )

        addExpenseViewModel.addTransaction.observe(this, { addTransaction ->
            if (addTransaction) {
                Toast.makeText(this, "Expense Added", Toast.LENGTH_SHORT).show()
                addExpenseViewModel.onTransactionComplete()
                onBackPressed()
            }
        })
    }

    private fun initIncomeData() {
        binding.toolbar.title = resources.getString(R.string.title_add_income)
        addIncomeViewModelFactory = AddIncomeViewModelFactory(walletDatabase.incomeDao,
            walletDatabase.transactionDao,
            application)
        addIncomeViewModel = ViewModelProvider(this, addIncomeViewModelFactory).get(
            AddIncomeViewModel::class.java
        )

        addIncomeViewModel.addTransaction.observe(this, { addTransaction ->
            if (addTransaction) {
                Toast.makeText(this, "Income Added", Toast.LENGTH_SHORT).show()
                addIncomeViewModel.onTransactionComplete()
                onBackPressed()
            }
        })
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

        binding.buttonAddAmount.setOnClickListener {
            val amount = binding.etAmount.text.toString()
            val title = binding.etTitle.text.toString()
            val date = selectedDate
            val type = transactionType
            val createdDate = Date()

            if (validateForm(amount, date)) {
                if (transactionType == "expense") {
                    binding.progressCircular.visibility = View.VISIBLE
                    binding.buttonAddAmount.isEnabled = false

                    val dbExpense = DbExpense(category = transactionCategory.name,
                        amount = amount.toLong(),
                        title = title,
                        date = date,
                        type = type,
                        month = selectedMonth,
                        year = selectedYear,
                        categoryIcon = transactionCategory.icon,
                        createdDate = createdDate)

                    val dbTransaction = DbTransaction(category = transactionCategory.name,
                        amount = amount.toLong(),
                        title = title,
                        date = date,
                        type = type,
                        month = selectedMonth,
                        year = selectedYear,
                        categoryIcon = transactionCategory.icon,
                        createdDate = createdDate)

                    addExpenseViewModel.addExpenseAmount(dbExpense, dbTransaction)

                } else if (transactionType == "income") {
                    binding.progressCircular.visibility = View.VISIBLE
                    binding.buttonAddAmount.isEnabled = false

                    val dbIncome = DbIncome(category = transactionCategory.name,
                        amount = amount.toLong(),
                        title = title,
                        date = date,
                        type = type,
                        month = selectedMonth,
                        year = selectedYear,
                        categoryIcon = transactionCategory.icon,
                        createdDate = createdDate)

                    val dbTransaction = DbTransaction(category = transactionCategory.name,
                        amount = amount.toLong(),
                        title = title,
                        date = date,
                        type = type,
                        month = selectedMonth,
                        year = selectedYear,
                        categoryIcon = transactionCategory.icon,
                        createdDate = createdDate)

                    addIncomeViewModel.addIncomeAmount(dbIncome, dbTransaction)
                }
            }
        }
    }

    private fun validateForm(amount: String, date: Date): Boolean {
        return when {
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
        binding.tvCategory.text = category.name
        binding.imageViewCategory.setImageDrawable(ResourcesCompat.getDrawable(resources,
            category.icon,
            resources.newTheme()))
        //  Toast.makeText(this, "Category selected is ${category.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onDateSelected(calendar: Calendar) {
        selectedDate = Date(calendar.timeInMillis)
        selectedMonth = getMonthString(calendar.get(Calendar.MONTH))
        selectedYear = calendar.get(Calendar.YEAR)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        binding.tvDate.text = getString(R.string.selected_date, day, selectedMonth, selectedYear)
    }

}