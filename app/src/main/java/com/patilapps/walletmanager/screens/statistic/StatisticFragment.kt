package com.patilapps.walletmanager.screens.statistic

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.patilapps.walletmanager.R
import com.patilapps.walletmanager.database.WalletDatabase
import com.patilapps.walletmanager.databinding.FragmentStatisticBinding
import com.patilapps.walletmanager.utils.WalletPreference
import java.text.DecimalFormat


class StatisticFragment : Fragment() {

    private lateinit var statisticViewModel: StatisticViewModel

    private lateinit var binding: FragmentStatisticBinding
    private lateinit var walletDatabase: WalletDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentStatisticBinding.inflate(inflater, container, false)

        val currency =
            WalletPreference.getStringPreference(requireContext(), WalletPreference.CURRENCY_SYMBOL)

        // setupCurrency(currency)
        val application = Application()
        walletDatabase = WalletDatabase.getInstance(requireContext())

        // Custom factory Class to access Dao
        val statisticViewModelFactory = StatisticViewModelFactory(application,
            walletDatabase.transactionDao,
            walletDatabase.yearDao)

        statisticViewModel =
            ViewModelProvider(this, statisticViewModelFactory).get(StatisticViewModel::class.java)

        val adapter = StatisticAdapter(currency) {
            val navDirection =
                StatisticFragmentDirections.actionStatisticFragmentToMonthDataFragment(it,
                    statisticViewModel.currentYear.value!!)
            findNavController().navigate(navDirection)
        }
        binding.listStatistics.adapter = adapter

        setupClickListeners()

        statisticViewModel.currentYear.observe(this.viewLifecycleOwner, {
            it?.let {
                binding.tvYear.text = it.toString()
                binding.statisticChart.invalidate()
                statisticViewModel.getAllTransactionFromYear(it)
            }
        })

        statisticViewModel.entryList.observe(viewLifecycleOwner, {
            it?.let {
                if (it.size > 0) {
                    val colors: ArrayList<Int> = ArrayList()
                    colors.add(ContextCompat.getColor(requireContext(), R.color.primary))
                    colors.add(ContextCompat.getColor(requireContext(), R.color.secondary))

                    val pieDataSet = PieDataSet(it, "")

                    val valueFormater = object : ValueFormatter(){
                        override fun getFormattedValue(value: Float): String {
                            val formattter = DecimalFormat("##.00")
                            val newValue = formattter.format(value)
                            return "$newValue%"
                        }
                    }
                    val pieData = PieData(pieDataSet)

                    pieData.setValueFormatter(valueFormater)
                    //setting text size of the value
                    pieDataSet.valueTextSize = 12f
                    //providing color list for coloring different entries
                    pieDataSet.colors = colors

                    pieData.setValueTextColor(ContextCompat.getColor(requireContext(),
                        R.color.white))
                    //grouping the data set from entry to chart
                    //showing the value of the entries, default true if not set
                    pieData.setDrawValues(true)

                    binding.statisticChart.data = pieData
                    binding.statisticChart.description.isEnabled = false
                    binding.statisticChart.invalidate()
                } else {
                    binding.statisticChart.clear()
                }
            }
        })

        statisticViewModel.statisticsList.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })

        /*  statisticViewModel.decTotalTransaction.observe(viewLifecycleOwner,
              {
                  if (it != 0) {
                      binding.cardDec.visibility = View.VISIBLE
                      binding.tvDecAmount.text = statisticViewModel.decTotalAmount.toString()
                      binding.tvDecTotalExpenseValue.text = statisticViewModel.decExpense.toString()
                      binding.tvDecTotalIncomeValue.text = statisticViewModel.decIncome.toString()
                      binding.tvDecTotalTransactions.text =
                          getString(R.string.format_total_transactions,
                              statisticViewModel.decTotalTransaction.value)
                      binding.tvDecLastAdded.text = statisticViewModel.decLastModified
                  } else {
                      binding.cardDec.visibility = View.GONE
                  }
              })
  */

        return binding.root
    }

    private fun setupClickListeners() {
        binding.buttonPreviousYear.setOnClickListener {
            statisticViewModel.onPreviousYearClick()
        }

        binding.buttonNextYear.setOnClickListener {
            statisticViewModel.onNextYearClick()
        }

        /*     binding.cardJan.setOnClickListener {
                 val navDirection = statisticViewModel.let {
                     StatisticFragmentDirections.actionStatisticFragmentToMonthDataFragment("Jan",
                         it.currentYear.value!!,
                         it.janIncome,
                         it.janExpense,
                         it.janTotalAmount,
                         it.janTotalTransaction.value!!)
                 }
                 findNavController().navigate(navDirection)
             }*/

    }

    /*private fun setupCurrency(currency: String) {
        binding.tvJanCurrency.text = currency
        binding.tvJanIncomeCurrency.text = currency
        binding.tvJanExpenseCurrency.text = currency
    }*/
}