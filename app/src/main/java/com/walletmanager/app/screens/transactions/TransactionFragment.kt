package com.walletmanager.app.screens.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.walletmanager.app.R
import com.walletmanager.app.databinding.FragmentAllTransactionBinding
import com.walletmanager.app.database.WalletDatabase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TransactionFragment : Fragment() {

    private lateinit var binding: FragmentAllTransactionBinding

    private lateinit var transactionViewModel: AllTransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_transaction, container, false)

        val walletDatabase = WalletDatabase.getInstance(requireContext())

        binding.setLifecycleOwner {
            viewLifecycleOwner.lifecycle
        }

        val transactionViewModelFactory = AllTransactionViewModelFactory(TransactionRepository(walletDatabase.transactionDao), walletDatabase.transactionDao)

        transactionViewModel = ViewModelProvider(this, transactionViewModelFactory).get(AllTransactionViewModel::class.java)

        val transactionAdapter = AllTransactionAdapter()

        binding.listAllTransactions.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.listAllTransactions.adapter = transactionAdapter

        lifecycle.coroutineScope.launch{
            transactionViewModel.transactionByPage.collectLatest {
                transactionAdapter.submitData(it)
                Toast.makeText(requireContext(), "List length : $it", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}