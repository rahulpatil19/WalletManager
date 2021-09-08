package com.patilapps.walletmanager.screens.wallet

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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