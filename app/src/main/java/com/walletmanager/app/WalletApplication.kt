package com.walletmanager.app

import android.app.Application
import android.content.SharedPreferences
import com.walletmanager.app.database.WalletDatabase

class WalletApplication: Application() {

    companion object{
        lateinit var walletDatabase: WalletDatabase
    }

    override fun onCreate() {
        super.onCreate()
    }
}