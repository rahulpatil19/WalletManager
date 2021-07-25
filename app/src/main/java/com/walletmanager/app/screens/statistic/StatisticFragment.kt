package com.walletmanager.app.screens.statistic

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.walletmanager.app.R
import com.walletmanager.app.databinding.FragmentHistoryBinding
import com.walletmanager.app.database.WalletDatabase

class StatisticFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel

    private lateinit var binding: FragmentHistoryBinding

    private lateinit var walletDatabase: WalletDatabase


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)

        val application = Application()

        walletDatabase = WalletDatabase.getInstance(requireContext())

        val transactionDao = walletDatabase.transactionDao

        val viewModelFactory = HistoryViewModelFactory(application, transactionDao)

        historyViewModel = ViewModelProvider(viewModelStore, viewModelFactory).get(HistoryViewModel::class.java)


        val adapter = HistoryAdapter()

        binding.listHistory.adapter = adapter

        historyViewModel.transactionList.observe(this.viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        return binding.root
    }
}