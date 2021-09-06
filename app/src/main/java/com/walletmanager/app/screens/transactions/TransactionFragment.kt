package com.walletmanager.app.screens.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.walletmanager.app.R
import com.walletmanager.app.databinding.FragmentAllTransactionBinding
import com.walletmanager.app.database.WalletDatabase
import com.walletmanager.app.database.entities.asDomainTransaction
import com.walletmanager.app.screens.delete.DeleteBottomSheetFragment
import com.walletmanager.app.utils.WalletPreference
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
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_all_transaction, container, false)

        val currency =
            WalletPreference.getStringPreference(requireContext(), WalletPreference.CURRENCY_SYMBOL)

        val walletDatabase = WalletDatabase.getInstance(requireContext())

        setHasOptionsMenu(true)

        binding.setLifecycleOwner {
            viewLifecycleOwner.lifecycle
        }

        val transactionViewModelFactory =
            AllTransactionViewModelFactory(TransactionRepository(walletDatabase.transactionDao),
                walletDatabase.transactionDao)

        transactionViewModel = ViewModelProvider(this,
            transactionViewModelFactory).get(AllTransactionViewModel::class.java)

        val transactionAdapter =
            AllTransactionAdapter(currency) { dbTransaction, action ->
                if (action == "edit") {
                    val transactionFragmentDirections =
                        TransactionFragmentDirections.actionAllTransactionFragmentToUpdateTransactionActivity(
                            dbTransaction.asDomainTransaction())
                    findNavController().navigate(transactionFragmentDirections)
                } else if (action == "delete") {
                    val fragmentManger = childFragmentManager
                    val fragment =
                        DeleteBottomSheetFragment.newInstance(dbTransaction.asDomainTransaction())
                    fragment.show(fragmentManger, "delete")
                }
            }

        binding.listAllTransactions.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.listAllTransactions.adapter = transactionAdapter

        lifecycle.coroutineScope.launch {
            transactionViewModel.transactionByPage.collectLatest {
                transactionAdapter.submitData(it)
            }
        }
        
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            findNavController().navigate(R.id.action_allTransactionFragment_to_homeFragment)
            return true;
        }
        return super.onOptionsItemSelected(item)
    }

}