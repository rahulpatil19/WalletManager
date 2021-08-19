package com.walletmanager.app.screens.wallet

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.walletmanager.app.database.WalletDatabase
import com.walletmanager.app.databinding.FragmentWalletBinding

class WalletFragment : Fragment() {

    private lateinit var binding: FragmentWalletBinding

    private lateinit var walletViewModel: WalletViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentWalletBinding.inflate(inflater)

        // ViewModel setup
        val walletDatabse = WalletDatabase.getInstance(requireContext())
        val viewModelFactory = WalletViewModelFactory(walletDatabse)

        walletViewModel = ViewModelProvider(this.viewModelStore,
            viewModelFactory).get(WalletViewModel::class.java)


        return binding.root
    }
}


class WalletPagerAdapter(fragment: WalletFragment, val yearList: List<String>): FragmentStateAdapter(fragment){
    override fun getItemCount(): Int {
        return yearList.size
    }

    override fun createFragment(position: Int): Fragment {
        return WalletMonthFragment()
    }


}