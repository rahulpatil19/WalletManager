package com.walletmanager.app.screens.monthlydata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.walletmanager.app.database.WalletDatabase
import com.walletmanager.app.databinding.FragmentMonthDataBinding

class MonthDataFragment : Fragment() {

    private lateinit var binding: FragmentMonthDataBinding

    private lateinit var viewModel: MonthDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMonthDataBinding.inflate(inflater, container, false)

        val navArgs: MonthDataFragmentArgs by navArgs()

        val month = navArgs.month
        val year = navArgs.year
        val totalAmount = navArgs.totalAmount
        val totalTransactions = navArgs.totalTransaction
        val income = navArgs.income
        val expense = navArgs.expense

        binding.tvMonthAndYear.text = "$month, $year"

        initializeData(totalAmount, income, expense, totalTransactions)

        val walletDatabase = WalletDatabase.getInstance(requireContext())

        val viewModelFactory = MonthDataViewModelFactory(month,
            year,
            walletDatabase.transactionDao,
            activity!!.application)

        viewModel = ViewModelProvider(this.viewModelStore,
            viewModelFactory).get(MonthDataViewModel::class.java)

        val monthDataAdapter = MonthDataAdapter()
        binding.listMonth.adapter = monthDataAdapter

        viewModel.transactionList.observe(this.viewLifecycleOwner, {
            monthDataAdapter.submitList(it)
        })

        return binding.root
    }

    private fun initializeData(totalAmount: Long, income: Long, expense: Long, totalTransactions: Int){
        binding.tvTotalAmountValue.text = totalAmount.toString()
        binding.tvTotalIncomeValue.text = income.toString()
        binding.tvTotalExpenseValue.text = expense.toString()
        if (totalTransactions == 1){
            binding.tvMonthTotalTransaction.text = "$totalTransactions transaction"
        }
        else{
            binding.tvMonthTotalTransaction.text = "$totalTransactions transactions"
        }
    }

}