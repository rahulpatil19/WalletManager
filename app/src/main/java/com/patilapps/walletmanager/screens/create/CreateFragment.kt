package com.patilapps.walletmanager.screens.create

import android.os.Bundle
import android.util.Log
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
import com.patilapps.walletmanager.databinding.FragmentCreateBinding
import com.patilapps.walletmanager.domain.Category
import com.patilapps.walletmanager.utils.WalletPreference
import com.patilapps.walletmanager.utils.getCurrentDate
import com.patilapps.walletmanager.utils.getMonthString
import java.util.*

class CreateFragment: Fragment(),  CategoryBottomSheetFragment.CateGoryClickListener,
    SelectDateBottomSheetFragment.DateClickListener  {

    private lateinit var transactionType: String

    private lateinit var binding: FragmentCreateBinding

    private lateinit var addIncomeViewModel: AddIncomeViewModel
    private lateinit var addIncomeViewModelFactory: AddIncomeViewModelFactory

    private lateinit var addExpenseViewModel: AddExpenseViewModel

    private lateinit var walletDatabase: WalletDatabase

    private lateinit var transactionCategory: Category

    private lateinit var selectedDate: Date
    private lateinit var selectedMonth: String
    private var selectedYear: Int = 2021


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCreateBinding.inflate(inflater, container, false)

        walletDatabase = WalletDatabase.getInstance(requireContext())

        val currencySymbol = WalletPreference.getStringPreference(requireContext(), WalletPreference.CURRENCY_SYMBOL)
        val currencyCode = WalletPreference.getStringPreference(requireContext(), WalletPreference.CURRENCY_CODE)

        val args : CreateFragmentArgs by navArgs()

            transactionType = args.transactionType

            val date = getCurrentDate()
            binding.tvDate.text = date

            binding.inputLayoutAmount.prefixText = currencySymbol
            binding.inputLayoutAmount.suffixText = currencyCode

            val calendar = Calendar.getInstance()
            selectedDate = Date(calendar.timeInMillis)
            selectedMonth = getMonthString(calendar.get(Calendar.MONTH))
            selectedYear = calendar.get(Calendar.YEAR)

            setupClickListeners()

            if (transactionType == "income") {
                initIncomeData()
            } else if (transactionType == "expense") {
                initExpenseData()
            }

        return binding.root
    }

    private fun initExpenseData() {
        binding.toolbar.title = resources.getString(R.string.title_add_expense)

        transactionCategory =
            Category("Travel", R.drawable.ic_category_travel, R.color.rebecca_purple)
        // default category setup
        binding.tvCategoryCreate.text = transactionCategory.name
        binding.ivCategoryCreate.setImageDrawable(ResourcesCompat.getDrawable(resources,
            transactionCategory.iconId, resources.newTheme()))

        binding.layoutCategoryCreate.background.setTint(ContextCompat.getColor(requireContext(), transactionCategory.colorId))

        val addExpenseViewModelFactory =
            AddExpenseViewModelFactory(walletDatabase.yearDao,
                walletDatabase.transactionDao,
                requireActivity().application)
        addExpenseViewModel = ViewModelProvider(this, addExpenseViewModelFactory).get(
            AddExpenseViewModel::class.java
        )

        addExpenseViewModel.addTransaction.observe(this.viewLifecycleOwner, { addTransaction ->
            if (addTransaction) {
                Toast.makeText(requireContext(), "Expense Added", Toast.LENGTH_SHORT).show()
                addExpenseViewModel.onTransactionComplete()
                findNavController().navigateUp()
            }
        })
    }

    private fun initIncomeData() {
        binding.toolbar.title = resources.getString(R.string.title_add_income)

        transactionCategory = Category("Salary", R.drawable.ic_category_money_bag, R.color.forest_green)

        // default category setup
        binding.tvCategoryCreate.text = transactionCategory.name

        binding.ivCategoryCreate.setImageDrawable(ResourcesCompat.getDrawable(resources,
            transactionCategory.iconId,
            resources.newTheme()))

        binding.layoutCategoryCreate.background.setTint(ContextCompat.getColor(requireContext(), transactionCategory.colorId))

        /*   binding.ivCategoryCreate.drawable.setTint(ContextCompat.getColor(this,
               transactionCategory.colorId))
   */
        Log.d("Category data", transactionCategory.toString())

        addIncomeViewModelFactory = AddIncomeViewModelFactory(walletDatabase.yearDao,
            walletDatabase.transactionDao,
            requireActivity().application)
        addIncomeViewModel = ViewModelProvider(this, addIncomeViewModelFactory).get(
            AddIncomeViewModel::class.java
        )

        addIncomeViewModel.addTransaction.observe(this.viewLifecycleOwner, { addTransaction ->
            if (addTransaction) {
                Toast.makeText(requireContext(), "Income Added", Toast.LENGTH_SHORT).show()
                addIncomeViewModel.onTransactionComplete()
                findNavController().navigateUp()
            }
        })
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

        binding.buttonAddAmount.setOnClickListener {
            val amount = binding.etAmount.text.toString()
            val title = binding.etTitle.text.toString()
            val date = selectedDate
            val type = transactionType
            val createdDate = Date()

            if (validateForm(title, amount, date)) {
                if (transactionType == "expense") {
                    binding.progressCircular.visibility = View.VISIBLE
                    binding.buttonAddAmount.isEnabled = false

                    val dbYear = DbYear(selectedYear)


                    val dbTransaction = DbTransaction(category = transactionCategory.name,
                        amount = amount.toLong(),
                        title = title,
                        date = date,
                        type = type,
                        month = selectedMonth,
                        year = selectedYear,
                        categoryIcon = transactionCategory.iconId,
                        categoryColor = transactionCategory.colorId,
                        createdDate = createdDate)

                    addExpenseViewModel.addExpenseAmount(dbYear, dbTransaction)

                } else if (transactionType == "income") {
                    binding.progressCircular.visibility = View.VISIBLE
                    binding.buttonAddAmount.isEnabled = false

                    val dbYear = DbYear(selectedYear)

                    val dbTransaction = DbTransaction(category = transactionCategory.name,
                        amount = amount.toLong(),
                        title = title,
                        date = date,
                        type = type,
                        month = selectedMonth,
                        year = selectedYear,
                        categoryIcon = transactionCategory.iconId,
                        categoryColor = transactionCategory.colorId,
                        createdDate = createdDate)

                    addIncomeViewModel.addIncomeAmount(dbYear, dbTransaction)
                }
            }
        }
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
        binding.tvCategoryCreate.text = category.name
        binding.layoutCategoryCreate.background.setTint(ContextCompat.getColor(requireContext(), transactionCategory.colorId))
        binding.ivCategoryCreate.setImageDrawable(ResourcesCompat.getDrawable(resources,
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
        sDay = if (day < 10) {
            "0$day"
        } else {
            "$day"
        }
        binding.tvDate.text = getString(R.string.selected_date, sDay, selectedMonth, selectedYear)
    }

}