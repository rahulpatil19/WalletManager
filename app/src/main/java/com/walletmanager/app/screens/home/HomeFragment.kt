package com.walletmanager.app.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.walletmanager.app.R
import com.walletmanager.app.adapters.RecentlyAddedAdapter
import com.walletmanager.app.databinding.FragmentHomeBinding
import com.walletmanager.app.database.WalletDatabase

class HomeFragment: Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var walletDatabase: WalletDatabase


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        walletDatabase = context?.let { WalletDatabase.getInstance(it) }!!
        val homeViewModelFactory = HomeViewModelFactory(walletDatabase.transactionDao, HomeRepository(walletDatabase.transactionDao))
        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner

        val recentlyAddedAdapter = RecentlyAddedAdapter()
        binding.listRecentlyAdded.adapter = recentlyAddedAdapter

        homeViewModel.recentlyAddedList.observe(viewLifecycleOwner, {
            recentlyAddedAdapter.submitList(it)
            homeViewModel.calculateAmount()
        })

        homeViewModel.totalAmount.observe(viewLifecycleOwner, {totalAmount ->
            binding.tvTotalAmountValue.text = totalAmount.toString()
        })

        homeViewModel.totalIncome.observe(viewLifecycleOwner, { totalIncome ->
            binding.tvTotalIncomeValue.text = totalIncome.toString()
        })

        homeViewModel.totalExpense.observe(viewLifecycleOwner, { totalExpense ->
            binding.tvTotalExpenseValue.text = totalExpense.toString()
        })

        binding.buttonAddTransaction.setOnClickListener {view->
            val action = HomeFragmentDirections.actionHomeFragmentToChooseTransactionSheetFragment()
            action?.let {
                view.findNavController().navigate(action)
            }
        }

        binding.tvRecentTransactionsSeeAll.setOnClickListener {
          //  val action = Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_allTransactionFragment)
            findNavController().navigate(R.id.action_homeFragment_to_allTransactionFragment)
        }

        return binding.root
    }
}