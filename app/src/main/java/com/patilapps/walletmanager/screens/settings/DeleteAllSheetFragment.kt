package com.patilapps.walletmanager.screens.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.patilapps.walletmanager.R
import com.patilapps.walletmanager.database.WalletDatabase
import com.patilapps.walletmanager.databinding.BottomSheetDeleteAllBinding

class DeleteAllSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetDeleteAllBinding

    private lateinit var walletDatabase: WalletDatabase

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = BottomSheetDeleteAllBinding.inflate(inflater, container, false)

        walletDatabase = WalletDatabase.getInstance(requireContext())

        val viewModelFactory =
            SettingsViewModelFactory(requireActivity().application, walletDatabase)

        viewModel = ViewModelProvider(this.viewModelStore,
            viewModelFactory).get(SettingsViewModel::class.java)


        binding.buttonCancel.setOnClickListener {
            dismiss()
        }

        binding.buttonDelete.setOnClickListener {
            viewModel.deleteAll()
            Toast.makeText(requireContext(),
                getString(R.string.all_transaction_cleard),
                Toast.LENGTH_SHORT)
                .show()
            dismiss()
        }

        return binding.root
    }
}