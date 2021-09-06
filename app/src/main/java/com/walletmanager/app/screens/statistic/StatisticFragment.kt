package com.walletmanager.app.screens.statistic

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.walletmanager.app.R
import com.walletmanager.app.database.WalletDatabase
import com.walletmanager.app.databinding.FragmentStatisticBinding
import com.walletmanager.app.utils.WalletPreference


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

        setupCurrency(currency)
        val application = Application()
        walletDatabase = WalletDatabase.getInstance(requireContext())

        // Custom factory Class to access Dao
        val statisticViewModelFactory = StatisticViewModelFactory(application,
            walletDatabase.transactionDao,
            walletDatabase.yearDao)

        statisticViewModel =
            ViewModelProvider(this, statisticViewModelFactory).get(StatisticViewModel::class.java)

        setupClickListeners()

        statisticViewModel.currentYear.observe(this.viewLifecycleOwner, {
            it?.let {
                binding.tvYear.text = it.toString()
                statisticViewModel.getAllTransactionFromYear(it)
            }
        })

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

    private fun setupClickListeners() {
        binding.buttonPreviousYear.setOnClickListener {
            statisticViewModel.onPreviousYearClick()
        }

        binding.buttonNextYear.setOnClickListener {
            statisticViewModel.onNextYearClick()
        }

        binding.cardJan.setOnClickListener {
            val navDirection = statisticViewModel.let {
                StatisticFragmentDirections.actionStatisticFragmentToMonthDataFragment("Jan",
                    it.currentYear.value!!,
                    it.janIncome,
                    it.janExpense,
                    it.janTotalAmount,
                    it.janTotalTransaction.value!!)
            }
            findNavController().navigate(navDirection)
        }


        binding.cardFeb.setOnClickListener {
            val navDirection = statisticViewModel.let {
                StatisticFragmentDirections.actionStatisticFragmentToMonthDataFragment("Feb",
                    it.currentYear.value!!,
                    it.febIncome,
                    it.febExpense,
                    it.febTotalAmount,
                    it.febTotalTransaction.value!!)
            }
            findNavController().navigate(navDirection)
        }


        binding.cardMar.setOnClickListener {
            val navDirection = statisticViewModel.let {
                StatisticFragmentDirections.actionStatisticFragmentToMonthDataFragment("Mar",
                    it.currentYear.value!!,
                    it.marIncome,
                    it.marExpense,
                    it.marTotalAmount,
                    it.marTotalTransaction.value!!)
            }
            findNavController().navigate(navDirection)
        }


        binding.cardApr.setOnClickListener {
            val navDirection = statisticViewModel.let {
                StatisticFragmentDirections.actionStatisticFragmentToMonthDataFragment("Apr",
                    it.currentYear.value!!,
                    it.aprIncome,
                    it.aprExpense,
                    it.aprTotalAmount,
                    it.aprTotalTransaction.value!!)
            }
            findNavController().navigate(navDirection)
        }


        binding.cardMay.setOnClickListener {
            val navDirection = statisticViewModel.let {
                StatisticFragmentDirections.actionStatisticFragmentToMonthDataFragment("May",
                    it.currentYear.value!!,
                    it.mayIncome,
                    it.mayExpense,
                    it.mayTotalAmount,
                    it.mayTotalTransaction.value!!)
            }
            findNavController().navigate(navDirection)
        }


        binding.cardJun.setOnClickListener {
            val navDirection = statisticViewModel.let {
                StatisticFragmentDirections.actionStatisticFragmentToMonthDataFragment("Jun",
                    it.currentYear.value!!,
                    it.junIncome,
                    it.junExpense,
                    it.junTotalAmount,
                    it.junTotalTransaction.value!!)
            }
            findNavController().navigate(navDirection)
        }


        binding.cardJul.setOnClickListener {
            val navDirection = statisticViewModel.let {
                StatisticFragmentDirections.actionStatisticFragmentToMonthDataFragment("Jul",
                    it.currentYear.value!!,
                    it.julIncome,
                    it.julExpense,
                    it.julTotalAmount,
                    it.julTotalTransaction.value!!)
            }
            findNavController().navigate(navDirection)
        }



        binding.cardAug.setOnClickListener {
            val navDirection = statisticViewModel.let {
                StatisticFragmentDirections.actionStatisticFragmentToMonthDataFragment("Aug",
                    it.currentYear.value!!,
                    it.augIncome,
                    it.augExpense,
                    it.augTotalAmount,
                    it.augTotalTransaction.value!!)
            }
            findNavController().navigate(navDirection)
        }


        binding.cardSep.setOnClickListener {
            val navDirection = statisticViewModel.let {
                StatisticFragmentDirections.actionStatisticFragmentToMonthDataFragment("Sep",
                    it.currentYear.value!!,
                    it.sepIncome,
                    it.sepExpense,
                    it.sepTotalAmount,
                    it.sepTotalTransaction.value!!)
            }
            findNavController().navigate(navDirection)
        }


        binding.cardOct.setOnClickListener {
            val navDirection = statisticViewModel.let {
                StatisticFragmentDirections.actionStatisticFragmentToMonthDataFragment("Oct",
                    it.currentYear.value!!,
                    it.octIncome,
                    it.octExpense,
                    it.octTotalAmount,
                    it.octTotalTransaction.value!!)
            }
            findNavController().navigate(navDirection)
        }


        binding.cardNov.setOnClickListener {
            val navDirection = statisticViewModel.let {
                StatisticFragmentDirections.actionStatisticFragmentToMonthDataFragment("Nov",
                    it.currentYear.value!!,
                    it.novIncome,
                    it.novExpense,
                    it.novTotalAmount,
                    it.novTotalTransaction.value!!)
            }
            findNavController().navigate(navDirection)
        }


        binding.cardDec.setOnClickListener {
            val navDirection = statisticViewModel.let {
                StatisticFragmentDirections.actionStatisticFragmentToMonthDataFragment("Dec",
                    it.currentYear.value!!,
                    it.decIncome,
                    it.decExpense,
                    it.decTotalAmount,
                    it.decTotalTransaction.value!!)
            }
            findNavController().navigate(navDirection)
        }
    }

    private fun setupCurrency(currency: String){
        binding.tvJanCurrency.text = currency
        binding.tvJanIncomeCurrency.text = currency
        binding.tvJanExpenseCurrency.text = currency

        binding.tvFebCurrency.text = currency
        binding.tvFebIncomeCurrency.text = currency
        binding.tvFebExpenseCurrency.text = currency

        binding.tvMarCurrency.text = currency
        binding.tvMarIncomeCurrency.text = currency
        binding.tvMarExpenseCurrency.text = currency

        binding.tvAprCurrency.text = currency
        binding.tvAprIncomeCurrency.text = currency
        binding.tvAprExpenseCurrency.text = currency

        binding.tvMayCurrency.text = currency
        binding.tvMayIncomeCurrency.text = currency
        binding.tvMayExpenseCurrency.text = currency

        binding.tvJunCurrency.text = currency
        binding.tvJunIncomeCurrency.text = currency
        binding.tvJunExpenseCurrency.text = currency

        binding.tvJulCurrency.text = currency
        binding.tvJulIncomeCurrency.text = currency
        binding.tvJulExpenseCurrency.text = currency

        binding.tvAugCurrency.text = currency
        binding.tvAugIncomeCurrency.text = currency
        binding.tvAugExpenseCurrency.text = currency

        binding.tvSepCurrency.text = currency
        binding.tvSepIncomeCurrency.text = currency
        binding.tvSepExpenseCurrency.text = currency

        binding.tvOctCurrency.text = currency
        binding.tvOctIncomeCurrency.text = currency
        binding.tvOctExpenseCurrency.text = currency

        binding.tvNovCurrency.text = currency
        binding.tvNovIncomeCurrency.text = currency
        binding.tvNovExpenseCurrency.text = currency

        binding.tvDecCurrency.text = currency
        binding.tvDecIncomeCurrency.text = currency
        binding.tvDecExpenseCurrency.text = currency
    }
}