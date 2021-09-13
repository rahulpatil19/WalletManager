package com.patilapps.walletmanager.screens.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.patilapps.walletmanager.R
import com.patilapps.walletmanager.database.WalletDatabase
import com.patilapps.walletmanager.database.entities.DbTransaction
import com.patilapps.walletmanager.database.entities.DbYear
import com.patilapps.walletmanager.databinding.FragmentUpdateTransactionBinding
import com.patilapps.walletmanager.domain.Category
import com.patilapps.walletmanager.domain.Transaction
import com.patilapps.walletmanager.screens.create.CategoryBottomSheetFragment
import com.patilapps.walletmanager.screens.create.SelectDateBottomSheetFragment
import com.patilapps.walletmanager.utils.WalletPreference
import com.patilapps.walletmanager.utils.formatSelectedDate
import com.patilapps.walletmanager.utils.getMonthString
import java.util.*

class UpdateTransactionFragment : Fragment(), CategoryBottomSheetFragment.CateGoryClickListener,
    SelectDateBottomSheetFragment.DateClickListener {

    private lateinit var binding: FragmentUpdateTransactionBinding

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

    private lateinit var navigatedFrom: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentUpdateTransactionBinding.inflate(inflater, container, false)

        walletDatabase = WalletDatabase.getInstance(requireContext())

        // Transaction to edit passed as safe args
        val args: UpdateTransactionFragmentArgs by navArgs()
        val transaction = args.transaction

        navigatedFrom = args.navigatedFrom

        // initialize transaction fields and update UI
        populateTransactionData(transaction)
        return binding.root
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

        val currencySymbol =
            WalletPreference.getStringPreference(requireContext(), WalletPreference.CURRENCY_SYMBOL)
        val currencyCode =
            WalletPreference.getStringPreference(requireContext(), WalletPreference.CURRENCY_CODE)

        binding.inputLayoutAmount.prefixText = currencySymbol
        binding.inputLayoutAmount.suffixText = currencyCode

        binding.tvCategoryUpdate.text = transaction.category
        binding.etAmount.setText(transaction.amount.toString())
        binding.etTitle.setText(transaction.title)
        binding.tvDate.setText(formatSelectedDate(transaction.date))
        binding.ivCategoryUpdate.setImageDrawable(ResourcesCompat.getDrawable(resources,
            transaction.categoryIcon, resources.newTheme()))

        binding.layoutCategoryUpdate.background.setTint(ContextCompat.getColor(requireContext(),
            transactionCategory.colorId))

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
            childFragmentManager.let {
                categoryBottomSheetFragment.show(it, "category")
            }
        }

        binding.rlChooseDate.setOnClickListener {
            val selectDateBottomSheetFragment = SelectDateBottomSheetFragment()
            childFragmentManager.let {
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
            UpdateExpenseViewModelFactory(requireActivity().application,
                walletDatabase.transactionDao,
                walletDatabase.yearDao
            )

        updateExpenseViewModel = ViewModelProvider(this, updateExpenseViewModelFactory).get(
            UpdateExpenseViewModel::class.java
        )

        updateExpenseViewModel.updateTransaction.observe(this.viewLifecycleOwner,
            { updateTransaction ->
                if (updateTransaction) {
                    if (navigatedFrom == "month") {
                        findNavController().navigate(R.id.action_updateTransactionFragment_to_statisticFragment)
                    } else {
                        findNavController().navigate(R.id.action_updateTransactionFragment_to_homeFragment)
                    }
                }
            })
    }

    private fun initIncomeData() {
        binding.toolbar.title = resources.getString(R.string.title_update_income)

        val updateIncomeViewModelFactory =
            UpdateIncomeViewModelFactory(requireActivity().application,
                walletDatabase.transactionDao,
                walletDatabase.yearDao)

        updateIncomeViewModel = ViewModelProvider(this, updateIncomeViewModelFactory).get(
            UpdateIncomeViewModel::class.java
        )

        updateIncomeViewModel.updateTransaction.observe(this.viewLifecycleOwner, { addTransaction ->
            if (addTransaction) {
                Toast.makeText(requireContext(), "Income Updated", Toast.LENGTH_SHORT).show()
                if (navigatedFrom == "month") {
                    findNavController().navigate(R.id.action_updateTransactionFragment_to_statisticFragment)
                } else {
                    findNavController().navigate(R.id.action_updateTransactionFragment_to_homeFragment)
                }
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
        binding.layoutCategoryUpdate.background.setTint(ContextCompat.getColor(requireContext(),
            transactionCategory.colorId))
        binding.ivCategoryUpdate.setImageDrawable(ResourcesCompat.getDrawable(resources,
            category.iconId,
            resources.newTheme()))
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

}