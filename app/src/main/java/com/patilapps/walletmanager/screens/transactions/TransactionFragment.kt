package com.patilapps.walletmanager.screens.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.patilapps.walletmanager.R
import com.patilapps.walletmanager.databinding.FragmentAllTransactionBinding
import com.patilapps.walletmanager.database.WalletDatabase
import com.patilapps.walletmanager.database.entities.DbTransaction
import com.patilapps.walletmanager.database.entities.asDomainTransaction
import com.patilapps.walletmanager.screens.delete.DeleteBottomSheetFragment
import com.patilapps.walletmanager.utils.WalletPreference
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TransactionFragment : Fragment(), DeleteBottomSheetFragment.DeleteClickListener {

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
                        TransactionFragmentDirections.actionAllTransactionFragmentToUpdateTransactionFragment(
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

    override fun onTransactionDelete(transaction: DbTransaction) {
        Toast.makeText(requireContext(), "${transaction.title} deleted", Toast.LENGTH_SHORT).show()
    }

/*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.toolbar
            .setupWithNavController(navController, appBarConfiguration)
    }
*/


}