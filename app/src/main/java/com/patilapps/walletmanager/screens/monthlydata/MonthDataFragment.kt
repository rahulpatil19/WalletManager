package com.patilapps.walletmanager.screens.monthlydata

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.patilapps.walletmanager.R
import com.patilapps.walletmanager.database.WalletDatabase
import com.patilapps.walletmanager.database.entities.DbTransaction
import com.patilapps.walletmanager.database.entities.DbYear
import com.patilapps.walletmanager.databinding.FragmentMonthDataBinding
import com.patilapps.walletmanager.domain.asDbTransaction
import com.patilapps.walletmanager.screens.delete.DeleteBottomSheetFragment
import com.patilapps.walletmanager.utils.TransactionBroadCast
import com.patilapps.walletmanager.utils.WalletPreference

class MonthDataFragment : Fragment(), DeleteBottomSheetFragment.DeleteClickListener {

    private lateinit var binding: FragmentMonthDataBinding

    private lateinit var viewModel: MonthDataViewModel

    private var totalTransactions: Int = 0

    private lateinit var selectedTransactionForUpdate: DbTransaction

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("MonthFragment State:", "onViewCreated()")

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

        Log.d("MonthFragment State:", "onCreateView()")

        setHasOptionsMenu(true)

        val navArgs: MonthDataFragmentArgs by navArgs()

        val currency =
            WalletPreference.getStringPreference(requireContext(), WalletPreference.CURRENCY_SYMBOL)

        val statistic = navArgs.statistic

        val month = statistic.month
        val year = navArgs.year
        val totalAmount = statistic.totalAmount
        totalTransactions = statistic.totalTransactions
        val income = statistic.totalIncome
        val expense = statistic.totalExpense

        initializeData(totalAmount, income, expense, totalTransactions, currency, year, month)

        val walletDatabase = WalletDatabase.getInstance(requireContext())

        val viewModelFactory = MonthDataViewModelFactory(month.take(3),
            year,
            totalAmount,
            income,
            expense,
            walletDatabase.transactionDao,
            activity!!.application)

        viewModel = ViewModelProvider(this.viewModelStore,
            viewModelFactory).get(MonthDataViewModel::class.java)

        val monthDataAdapter = MonthDataAdapter(currency) { transaction, action ->
            if (action == "edit") {
                viewModel.selectedTransaction.value = transaction.asDbTransaction()
                val navDirections =
                    MonthDataFragmentDirections.actionMonthDataFragmentToUpdateTransactionFragment(
                        transaction, "month")
                findNavController().navigate(navDirections)
            } else if (action == "delete") {
                val fragmentManager = childFragmentManager
                val fragment = DeleteBottomSheetFragment.newInstance(transaction)
                fragment.show(fragmentManager, "delete")
            }
        }
/*

        viewModel.selectedTransaction.observe(this.viewLifecycleOwner, {
            viewModel.updateAmountValues(it)
        })
*/

        binding.listMonth.adapter = monthDataAdapter

        viewModel.transactionList.observe(this.viewLifecycleOwner, {
            monthDataAdapter.submitList(it)
        })

        viewModel.totalAmountLiveData.observe(this.viewLifecycleOwner, {
            binding.tvTotalAmountValue.text = it.toString()
        })

        viewModel.incomeAmountLiveData.observe(this.viewLifecycleOwner, {
            binding.tvTotalIncomeValue.text = it.toString()
        })

        viewModel.expenseAmountLiveData.observe(this.viewLifecycleOwner, {
            binding.tvTotalExpenseValue.text = it.toString()
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
        month: String,
    ) {

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

        totalTransactions = totalTransactions.minus(1)
        if (totalTransactions == 1) {
            binding.tvMonthTotalTransaction.text = "$totalTransactions transaction"
        } else {
            binding.tvMonthTotalTransaction.text = "$totalTransactions transactions"
        }

        Toast.makeText(requireContext(), "${transaction.title} deleted", Toast.LENGTH_SHORT).show()

        val previousTotalAmount = binding.tvTotalAmountValue.text.toString().toLong()

        val newTotalAmount = previousTotalAmount.minus(amount)
        binding.tvTotalAmountValue.text = newTotalAmount.toString()

        if (type == "income") {
            val previousIncomeAmount = binding.tvTotalIncomeValue.text.toString().toLong()
            val newIncomeAmount = previousIncomeAmount.minus(amount)
            binding.tvTotalIncomeValue.text = "$newIncomeAmount"
        } else if (type == "expense") {
            val previousExpenseAmount = binding.tvTotalExpenseValue.text.toString().toLong()
            val newExpenseAmount = previousExpenseAmount.minus(amount)
            binding.tvTotalExpenseValue.text = "$newExpenseAmount"
        }
    }

}