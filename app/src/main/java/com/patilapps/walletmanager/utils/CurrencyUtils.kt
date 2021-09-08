package com.patilapps.walletmanager.utils

import android.content.Context
import java.util.*

class CurrencyUtils {

    companion object {
        fun setDefaultCurrency(context: Context) {
            val locale = Locale.getDefault()
            val currency = Currency.getInstance(locale)
            val currencyCode = currency.currencyCode
            val name = currency.displayName
            val symbol = currency.symbol
            WalletPreference.storeCurrencyData(context, name, currencyCode, symbol, 0)
        }
    }

}