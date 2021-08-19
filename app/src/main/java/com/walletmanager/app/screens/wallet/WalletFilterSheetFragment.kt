package com.walletmanager.app.screens.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.walletmanager.app.R
import com.walletmanager.app.databinding.FragmentSheetWalletFilterBinding

class WalletFilterSheetFragment: BottomSheetDialogFragment() {

   // private lateinit var binding: FragmentSheetWalletFilterBinding

    private lateinit var filterType: String
/*

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

      //
    }
*/

    interface WalletFilterClickListener{
        fun onFilterOptionClick(filterType: String)
    }
}