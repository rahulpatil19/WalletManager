package com.patilapps.walletmanager.screens.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.patilapps.walletmanager.database.WalletDatabase
import com.patilapps.walletmanager.database.entities.DbTransaction
import com.patilapps.walletmanager.databinding.FragmentSearchBinding
import com.patilapps.walletmanager.screens.delete.DeleteBottomSheetFragment
import com.patilapps.walletmanager.screens.monthlydata.MonthDataFragmentDirections
import com.patilapps.walletmanager.utils.WalletPreference

class SearchFragment : Fragment(), SearchView.OnQueryTextListener, DeleteBottomSheetFragment.DeleteClickListener {

    private lateinit var binding: FragmentSearchBinding

    private lateinit var searchViewModel: SearchViewModel

    private lateinit var searchAdapter: SearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.searchView.setOnQueryTextListener(this)
        binding.searchView.setIconifiedByDefault(false)


        val currency =
            WalletPreference.getStringPreference(requireContext(), WalletPreference.CURRENCY_SYMBOL)

        val walletDatabase = WalletDatabase.getInstance(requireContext())

        val viewModelFactory =
            SearchViewModelFactory(requireActivity().application, walletDatabase.transactionDao)

        searchViewModel =
            ViewModelProvider(viewModelStore, viewModelFactory).get(SearchViewModel::class.java)

        binding.lifecycleOwner = this
        binding.searchViewModel = searchViewModel

        searchAdapter = SearchAdapter(currency) { transaction, action ->
            if (action == "edit") {
                val navDirections =
                    SearchFragmentDirections.actionSearchFragmentToUpdateTransactionFragment(
                        transaction)
                findNavController().navigate(navDirections)
            } else if (action == "delete") {
                val fragmentManager = childFragmentManager
                val fragment = DeleteBottomSheetFragment.newInstance(transaction)
                fragment.show(fragmentManager, "delete")
            }
        }

        binding.listItemSearch.adapter = searchAdapter

        searchViewModel.transactionList.observe(this.viewLifecycleOwner, {
            searchAdapter.submitList(it)
        })

        return binding.root
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            if (query.isNotEmpty()) {
                searchViewModel.getTransactionByKeyword(query)
            } else {
                searchViewModel.clearList()
            }
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            if (newText.isNotEmpty()) {
                searchViewModel.getTransactionByKeyword(newText)
            } else {
                searchViewModel.clearList()
            }
        }
        return true
    }

    override fun onTransactionDelete(transaction: DbTransaction) {
        Toast.makeText(requireContext(), "${transaction.title} deleted", Toast.LENGTH_SHORT).show()
    }
}