package com.patilapps.walletmanager.screens.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.patilapps.walletmanager.R
import com.patilapps.walletmanager.adapters.RecentlyAddedAdapter
import com.patilapps.walletmanager.databinding.FragmentHomeBinding
import com.patilapps.walletmanager.database.WalletDatabase
import com.patilapps.walletmanager.utils.WalletPreference

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var walletDatabase: WalletDatabase


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        walletDatabase = context?.let { WalletDatabase.getInstance(it) }!!

        val currency =
            WalletPreference.getStringPreference(requireContext(), WalletPreference.CURRENCY_SYMBOL)

        val homeViewModelFactory = HomeViewModelFactory(walletDatabase.transactionDao,
            HomeRepository(walletDatabase.transactionDao))
        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner

        val recentlyAddedAdapter = RecentlyAddedAdapter(currency) {
            val homeFragmentDirections =
                HomeFragmentDirections.actionHomeFragmentToUpdateTransactionActivity(it)
            findNavController().navigate(homeFragmentDirections)
        }

        binding.listRecentlyAdded.adapter = recentlyAddedAdapter

        homeViewModel.recentlyAddedList.observe(viewLifecycleOwner, {
            recentlyAddedAdapter.submitList(it)
            Log.d("Transaction List", it.toString())
            homeViewModel.calculateAmount()
            if (it.size > 0){
                binding.layoutNoTransaction.visibility = View.GONE
                binding.tvRecentTransactionsSeeAll.visibility = View.VISIBLE
            }
            else{
                binding.tvRecentTransactionsSeeAll.visibility = View.GONE
                binding.layoutNoTransaction.visibility = View.VISIBLE
            }
        })

        binding.tvCurrency.text = currency
        binding.tvCurrencyCode.text =
            WalletPreference.getStringPreference(requireContext(), WalletPreference.CURRENCY_CODE)
        binding.tvTotalIncomeCurrency.text = currency
        binding.tvTotalExpenseCurrency.text = currency

        homeViewModel.totalAmount.observe(viewLifecycleOwner, { totalAmount ->
            binding.tvTotalAmountValue.text = totalAmount.toString()
        })

        homeViewModel.totalIncome.observe(viewLifecycleOwner, { totalIncome ->
            binding.tvTotalIncomeValue.text = totalIncome.toString()
        })

        homeViewModel.totalExpense.observe(viewLifecycleOwner, { totalExpense ->
            binding.tvTotalExpenseValue.text = totalExpense.toString()
        })

        binding.buttonAddTransaction.setOnClickListener { view ->
            val action = HomeFragmentDirections.actionHomeFragmentToChooseTransactionSheetFragment()
            action.let {
                findNavController().navigate(action)
            }
        }

        binding.tvRecentTransactionsSeeAll.setOnClickListener {
            //  val action = Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_allTransactionFragment)
            findNavController().navigate(R.id.action_homeFragment_to_allTransactionFragment)
        }

        return binding.root
    }
}