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
import com.patilapps.walletmanager.databinding.FragmentMonthDataBinding
import com.patilapps.walletmanager.screens.delete.DeleteBottomSheetFragment
import com.patilapps.walletmanager.utils.WalletPreference

class MonthDataFragment : Fragment() {

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

        val month = navArgs.month
        val year = navArgs.year
        val totalAmount = navArgs.totalAmount
        val totalTransactions = navArgs.totalTransaction
        val income = navArgs.income
        val expense = navArgs.expense

        binding.tvMonthAndYear.text = "$month, $year"
        binding.tvCurrency.text = currency
        binding.tvTotalIncomeCurrency.text = getString(R.string.set_currency, "+$currency")
        binding.tvTotalExpenseCurrency.text = getString(R.string.set_currency, "-$currency")

        initializeData(totalAmount, income, expense, totalTransactions)

        val walletDatabase = WalletDatabase.getInstance(requireContext())

        val viewModelFactory = MonthDataViewModelFactory(month,
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
    ) {
        binding.tvTotalAmountValue.text = totalAmount.toString()
        binding.tvTotalIncomeValue.text = income.toString()
        binding.tvTotalExpenseValue.text = expense.toString()
        if (totalTransactions == 1) {
            binding.tvMonthTotalTransaction.text = "$totalTransactions transaction"
        } else {
            binding.tvMonthTotalTransaction.text = "$totalTransactions transactions"
        }
    }

}