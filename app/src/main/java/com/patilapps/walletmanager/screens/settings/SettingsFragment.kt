package com.patilapps.walletmanager.screens.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.patilapps.walletmanager.R
import com.patilapps.walletmanager.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding: FragmentSettingsBinding =
            FragmentSettingsBinding.inflate(inflater, container, false)

        binding.layoutSettingsDeleteAll.setOnClickListener {

            val deleteAllSheetFragment = DeleteAllSheetFragment()
            childFragmentManager.let {
                deleteAllSheetFragment.show(it, "delete")
            }
       //     findNavController().navigate(R.id.action_settingsFragment_to_deleteAllSheetFragment)
        }

        binding.layoutSettingsRateUs.setOnClickListener {
            rateUs()
        }

        binding.layoutSettingsAbout.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_aboutSheetFragment)
        }

        binding.layoutSettingsChangeCurrency.setOnClickListener {
            chooseCountry()
        }

        return binding.root
    }

    private fun chooseCountry() {
        /*   val picker = CurrencyPicker.newInstance("Choose currency")
           picker.setListener(CurrencyPickerListener { name, code, symbol, flagDrawableResourceId ->
               storeCurrencyData(requireContext(), name, code, symbol, flagDrawableResourceId)
               picker.dismiss()
           })

           picker.show(childFragmentManager, "Currency Picker")*/
        // findNavController().navigate(R.id.action_settingsFragment_to_currencySheetFragment)
        val selectDateBottomSheetFragment = CurrencySheetFragment()
        childFragmentManager.let {
            selectDateBottomSheetFragment.show(it, "currency")
        }
    }

    private fun rateUs() {
        val playStoreLink = "market://details?id=com.patilapps.walletmanager"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(playStoreLink)
        startActivity(intent)
    }

}