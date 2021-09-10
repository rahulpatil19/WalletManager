package com.patilapps.walletmanager.screens.monthlydata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.patilapps.walletmanager.R
import com.patilapps.walletmanager.database.WalletDatabase
import com.patilapps.walletmanager.database.entities.DbTransaction
import com.patilapps.walletmanager.database.entities.DbYear
import com.patilapps.walletmanager.databinding.FragmentMonthDataBinding
import com.patilapps.walletmanager.screens.delete.DeleteBottomSheetFragment
import com.patilapps.walletmanager.utils.WalletPreference

class MonthDataFragment : Fragment(), DeleteBottomSheetFragment.DeleteClickListener {

    private lateinit var binding: FragmentMonthDataBinding

    private lateinit var viewModel: MonthDataViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMonthDataBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        val navArgs: MonthDataFragmentArgs by navArgs()

        val currency =
            WalletPreference.getStringPreference(requireContext(), WalletPreference.CURRENCY_SYMBOL)

        val statistic = navArgs.statistic

        val month = statistic.month
        val year = navArgs.year
        val totalAmount = statistic.totalAmount
        val totalTransactions = statistic.totalTransactions
        val income = statistic.totalIncome
        val expense = statistic.totalExpense

        initializeData(totalAmount, income, expense, totalTransactions, currency, year, month)

        val walletDatabase = WalletDatabase.getInstance(requireContext())

        val viewModelFactory = MonthDataViewModelFactory(month.take(3),
            year,
            walletDatabase.transactionDao,
            activity!!.application)

        viewModel = ViewModelProvider(this.viewModelStore,
            viewModelFactory).get(MonthDataViewModel::class.java)

        val monthDataAdapter = MonthDataAdapter(currency) { transaction, action ->
            if (action == "edit") {
                val navDirections =
                    MonthDataFragmentDirections.actionMonthDataFragmentToUpdateTransactionActivity(
                        transaction)
                findNavController().navigate(navDirections)
            } else if (action == "delete") {
                val fragmentManager = childFragmentManager
                val fragment = DeleteBottomSheetFragment.newInstance(transaction)
                fragment.show(fragmentManager, "delete")
            }
        }

        binding.listMonth.adapter = monthDataAdapter

        viewModel.transactionList.observe(this.viewLifecycleOwner, {
            monthDataAdapter.submitList(it)
        })

        return binding.root
    }

    private fun initializeData(
        totalAmount: Long,
        income: Long,
        expense: Long,
        totalTransactions: Int,
        currency: String,
        year: Int,
        month: String
    ) {
        binding.tvTotalAmountValue.text = totalAmount.toString()
        binding.tvTotalIncomeValue.text = income.toString()
        binding.tvTotalExpenseValue.text = expense.toString()

        binding.tvMonthAndYear.text = "$month, $year"
        binding.tvCurrency.text = currency
        binding.tvTotalIncomeCurrency.text = currency
        binding.tvTotalExpenseCurrency.text = currency

        if (totalTransactions == 1) {
            binding.tvMonthTotalTransaction.text = "$totalTransactions transaction"
        } else {
            binding.tvMonthTotalTransaction.text = "$totalTransactions transactions"
        }
    }

    override fun onTransactionDelete(transaction: DbTransaction) {
        val type = transaction.type
        val amount = transaction.amount

        val previousTotalAmount = binding.tvTotalAmountValue.text.toString().toLong()

        val newTotalAmount = previousTotalAmount.minus(amount)
        binding.tvTotalAmountValue.text = newTotalAmount.toString()

        if (type == "income"){
            val previousIncomeAmount = binding.tvTotalIncomeValue.text.toString().toLong()
            val newIncomeAmount = previousIncomeAmount.minus(amount)
            binding.tvTotalIncomeValue.text = "$newIncomeAmount"
        }
        else if(type == "expense"){
            val previousExpenseAmount = binding.tvTotalExpenseValue.text.toString().toLong()
            val newExpenseAmount = previousExpenseAmount.minus(amount)
            binding.tvTotalExpenseValue.text = "$newExpenseAmount"
        }
    }

}