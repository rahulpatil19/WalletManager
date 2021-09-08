package com.patilapps.walletmanager

import android.app.Application
import com.patilapps.walletmanager.database.WalletDatabase

class WalletApplication: Application() {

    companion object{
        lateinit var walletDatabase: WalletDatabase
    }

    override fun onCreate() {
        super.onCreate()
    }
}