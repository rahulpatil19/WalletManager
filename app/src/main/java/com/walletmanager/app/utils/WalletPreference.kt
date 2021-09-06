package com.walletmanager.app.utils

import android.content.Context

object WalletPreference {

    private const val WALLET_PREFERENCE = "wallet_preference"

    const val FIRST_RUN = "preference_first_run"
    const val CURRENCY_NAME = "currency_name"
    const val CURRENCY_CODE = "currency_code"
    const val CURRENCY_SYMBOL = "currency_symbol"
    const val CURRENCY_FLAG_RESOURCE_ID = "currency_flag"

    fun storeCurrencyData(
        context: Context,
        name: String,
        code: String,
        symbol: String,
        flagDrawableResourceId: Int,
    ) {
        val sharedPreference = context.getSharedPreferences(WALLET_PREFERENCE, 0)
        val editor = sharedPreference.edit()
        editor.putString(CURRENCY_NAME, name)
        editor.putString(CURRENCY_CODE, code)
        editor.putString(CURRENCY_SYMBOL, symbol)
        editor.putInt(CURRENCY_FLAG_RESOURCE_ID, flagDrawableResourceId)
        editor.apply()
    }

    fun setBooleanPreference(context: Context, key: String, value: Boolean) {
        val sharedPreference = context.getSharedPreferences(WALLET_PREFERENCE, 0)
        val editor = sharedPreference.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getStringPreference(context: Context, key: String): String {
        val sharedPreferences =
            context.getSharedPreferences(WALLET_PREFERENCE, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, "").toString()
    }

    fun getBooleanPreference(context: Context, key: String): Boolean {
        val sharedPreferences =
            context.getSharedPreferences(WALLET_PREFERENCE, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, true)
    }

}