package com.walletmanager.app.screens.statistic

import android.R.attr
import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.walletmanager.app.R
import com.walletmanager.app.database.WalletDatabase
import com.walletmanager.app.databinding.FragmentStatisticBinding
import android.R.attr.label
import androidx.core.content.ContextCompat


class StatisticFragment : Fragment() {

    private lateinit var statisticViewModel: StatisticViewModel

    private lateinit var binding: FragmentStatisticBinding
    private lateinit var walletDatabase: WalletDatabase

    private var pieChartList = listOf<PieEntry>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentStatisticBinding.inflate(inflater, container, false)

        val application = Application()
        walletDatabase = WalletDatabase.getInstance(requireContext())

        // Custom factory Class to access Dao
        val statisticViewModelFactory = StatisticViewModelFactory(application,
            walletDatabase.transactionDao,
            walletDatabase.yearDao)

        statisticViewModel =
            ViewModelProvider(this, statisticViewModelFactory).get(StatisticViewModel::class.java)


        statisticViewModel.currentYear.observe(this.viewLifecycleOwner, {
            it?.let {
                binding.tvYear.text = it.toString()
                statisticViewModel.getAllTransactionFromYear(it)
            }
        })

        binding.buttonPreviousYear.setOnClickListener({
            statisticViewModel.onPreviousYearClick()
        })

        binding.buttonNextYear.setOnClickListener {
            statisticViewModel.onNextYearClick()
        }


        statisticViewModel.janTotalTransaction.observe(viewLifecycleOwner, {
            if (it != 0) {
                binding.cardJan.visibility = View.VISIBLE
                binding.tvJanAmount.text = statisticViewModel.janTotalAmount.toString()
                binding.tvJanTotalExpenseValue.text = statisticViewModel.janExpense.toString()
                binding.tvJanTotalIncomeValue.text = statisticViewModel.janIncome.toString()
                binding.tvJanTotalTransactions.text = getString(R.string.format_total_transactions,
                    statisticViewModel.janTotalTransaction.value)
                binding.tvJanLastAdded.text = statisticViewModel.janLastModified
            } else {
                binding.cardJan.visibility = View.GONE
            }
        })


        statisticViewModel.febTotalTransaction.observe(viewLifecycleOwner, {
            if (it != 0) {
                binding.cardFeb.visibility = View.VISIBLE
                binding.tvFebAmount.text = statisticViewModel.febTotalAmount.toString()
                binding.tvFebTotalExpenseValue.text = statisticViewModel.febExpense.toString()
                binding.tvFebTotalIncomeValue.text = statisticViewModel.febIncome.toString()
                binding.tvFebTotalTransactions.text = getString(R.string.format_total_transactions,
                    statisticViewModel.febTotalTransaction.value)
                binding.tvFebLastAdded.text = statisticViewModel.febLastModified
            } else {
                binding.cardFeb.visibility = View.GONE
            }
        })

        statisticViewModel.marTotalTransaction.observe(viewLifecycleOwner, {
            if (it != 0) {
                binding.cardMar.visibility = View.VISIBLE
                binding.tvMarAmount.text = statisticViewModel.marTotalAmount.toString()
                binding.tvMarTotalExpenseValue.text = statisticViewModel.marExpense.toString()
                binding.tvMarTotalIncomeValue.text = statisticViewModel.marIncome.toString()
                binding.tvMarTotalTransactions.text = getString(R.string.format_total_transactions,
                    statisticViewModel.marTotalTransaction.value)
                binding.tvMarLastAdded.text = statisticViewModel.marLastModified
            } else {
                binding.cardMar.visibility = View.GONE
            }
        })


        statisticViewModel.aprTotalTransaction.observe(viewLifecycleOwner, {
            if (it != 0) {
                binding.cardApr.visibility = View.VISIBLE
                binding.tvAprAmount.text = statisticViewModel.aprTotalAmount.toString()
                binding.tvAprTotalExpenseValue.text = statisticViewModel.aprExpense.toString()
                binding.tvAprTotalIncomeValue.text = statisticViewModel.aprIncome.toString()
                binding.tvAprTotalTransactions.text = getString(R.string.format_total_transactions,
                    statisticViewModel.aprTotalTransaction.value)
                binding.tvAprLastAdded.text = statisticViewModel.aprLastModified
            } else {
                binding.cardApr.visibility = View.GONE
            }
        })


        statisticViewModel.mayTotalTransaction.observe(viewLifecycleOwner, {
            if (it != 0) {
                binding.cardMay.visibility = View.VISIBLE
                binding.tvMayAmount.text = statisticViewModel.mayTotalAmount.toString()
                binding.tvMayTotalExpenseValue.text = statisticViewModel.mayExpense.toString()
                binding.tvMayTotalIncomeValue.text = statisticViewModel.mayIncome.toString()
                binding.tvMayTotalTransactions.text = getString(R.string.format_total_transactions,
                    statisticViewModel.mayTotalTransaction.value)
                binding.tvMayLastAdded.text = statisticViewModel.mayLastModified
            } else {
                binding.cardMay.visibility = View.GONE
            }
        })


        statisticViewModel.junTotalTransaction.observe(viewLifecycleOwner, {
            if (it != 0) {
                binding.cardJun.visibility = View.VISIBLE
                binding.tvJunAmount.text = statisticViewModel.junTotalAmount.toString()
                binding.tvJunTotalExpenseValue.text = statisticViewModel.junExpense.toString()
                binding.tvJunTotalIncomeValue.text = statisticViewModel.junIncome.toString()
                binding.tvJunTotalTransactions.text = getString(R.string.format_total_transactions,
                    statisticViewModel.junTotalTransaction.value)
                binding.tvJunLastAdded.text = statisticViewModel.junLastModified
            } else {
                binding.cardJun.visibility = View.GONE
            }
        })


        statisticViewModel.julTotalTransaction.observe(viewLifecycleOwner, {
            if (it != 0) {
                binding.cardJul.visibility = View.VISIBLE
                binding.tvJulAmount.text = statisticViewModel.julTotalAmount.toString()
                binding.tvJulTotalExpenseValue.text = statisticViewModel.julExpense.toString()
                binding.tvJulTotalIncomeValue.text = statisticViewModel.julIncome.toString()
                binding.tvJulTotalTransactions.text = getString(R.string.format_total_transactions,
                    statisticViewModel.julTotalTransaction.value)
                binding.tvJulLastAdded.text = statisticViewModel.julLastModified
            } else {
                binding.cardJul.visibility = View.GONE
            }
        })


        statisticViewModel.augTotalTransaction.observe(viewLifecycleOwner, {
            if (it != 0) {
                binding.cardAug.visibility = View.VISIBLE
                binding.tvAugAmount.text = statisticViewModel.augTotalAmount.toString()
                binding.tvAugTotalExpenseValue.text = statisticViewModel.augExpense.toString()
                binding.tvAugTotalIncomeValue.text = statisticViewModel.augIncome.toString()
                binding.tvAugTotalTransactions.text = getString(R.string.format_total_transactions,
                    statisticViewModel.augTotalTransaction.value)
                binding.tvAugLastAdded.text = statisticViewModel.augLastModified
            } else {
                binding.cardAug.visibility = View.GONE
            }
        })


        statisticViewModel.sepTotalTransaction.observe(viewLifecycleOwner, {
            if (it != 0) {
                binding.cardSep.visibility = View.VISIBLE
                binding.tvSepAmount.text = statisticViewModel.sepTotalAmount.toString()
                binding.tvSepTotalExpenseValue.text = statisticViewModel.sepExpense.toString()
                binding.tvSepTotalIncomeValue.text = statisticViewModel.sepIncome.toString()
                binding.tvSepTotalTransactions.text = getString(R.string.format_total_transactions,
                    statisticViewModel.sepTotalTransaction.value)
                binding.tvSepLastAdded.text = statisticViewModel.sepLastModified
            } else {
                binding.cardSep.visibility = View.GONE
            }
        })


        statisticViewModel.octTotalTransaction.observe(viewLifecycleOwner, {
            if (it != 0) {
                binding.cardOct.visibility = View.VISIBLE
                binding.tvOctAmount.text = statisticViewModel.octTotalAmount.toString()
                binding.tvOctTotalExpenseValue.text = statisticViewModel.octExpense.toString()
                binding.tvOctTotalIncomeValue.text = statisticViewModel.octIncome.toString()
                binding.tvOctTotalTransactions.text = getString(R.string.format_total_transactions,
                    statisticViewModel.octTotalTransaction.value)
                binding.tvOctLastAdded.text = statisticViewModel.octLastModified
            } else {
                binding.cardOct.visibility = View.GONE
            }
        })


        statisticViewModel.novTotalTransaction.observe(viewLifecycleOwner, {
            if (it != 0) {
                binding.cardNov.visibility = View.VISIBLE
                binding.tvNovAmount.text = statisticViewModel.novTotalAmount.toString()
                binding.tvNovTotalExpenseValue.text = statisticViewModel.novExpense.toString()
                binding.tvNovTotalIncomeValue.text = statisticViewModel.novIncome.toString()
                binding.tvNovTotalTransactions.text = getString(R.string.format_total_transactions,
                    statisticViewModel.novTotalTransaction.value)
                binding.tvNovLastAdded.text = statisticViewModel.novLastModified
            } else {
                binding.cardNov.visibility = View.GONE
            }
        })

        statisticViewModel.decTotalTransaction.observe(viewLifecycleOwner, {
            if (it != 0) {
                binding.cardDec.visibility = View.VISIBLE
                binding.tvDecAmount.text = statisticViewModel.decTotalAmount.toString()
                binding.tvDecTotalExpenseValue.text = statisticViewModel.decExpense.toString()
                binding.tvDecTotalIncomeValue.text = statisticViewModel.decIncome.toString()
                binding.tvDecTotalTransactions.text = getString(R.string.format_total_transactions,
                    statisticViewModel.decTotalTransaction.value)
                binding.tvDecLastAdded.text = statisticViewModel.decLastModified
            } else {
                binding.cardDec.visibility = View.GONE
            }
        })

        statisticViewModel.entryList.observe(viewLifecycleOwner, {
            val colors: ArrayList<Int> = ArrayList()
            colors.add(ContextCompat.getColor(requireContext(), R.color.green))
            colors.add(ContextCompat.getColor(requireContext(), R.color.red_1))

            val pieDataSet = PieDataSet(it!!, "")
            val pieData = PieData(pieDataSet)
            //setting text size of the value
            pieDataSet.valueTextSize = 12f
            //providing color list for coloring different entries
            pieDataSet.colors = colors

            pieData.setValueTextColor(ContextCompat.getColor(requireContext(), R.color.white))

            //grouping the data set from entry to chart
            //showing the value of the entries, default true if not set
            pieData.setDrawValues(true)
            binding.statisticChart.data = pieData
            binding.statisticChart.invalidate()
        })


        return binding.root
    }
}