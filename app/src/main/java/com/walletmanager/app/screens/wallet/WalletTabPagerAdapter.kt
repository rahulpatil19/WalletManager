package com.walletmanager.app.screens.wallet

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class WalletTabPagerAdapter(
    fragment: WalletFragment,
    val list: List<String>,
    val filterType: String,
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        if (filterType.equals("year")) {
            return WalletYearFragment()
        }
        else if (filterType.equals("month")){
            return WalletMonthFragment()
        }
        else{
            return WalletCategoryFragment()
        }
    }
}