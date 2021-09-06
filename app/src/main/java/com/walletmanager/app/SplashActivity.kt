package com.walletmanager.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.walletmanager.app.utils.CurrencyUtils
import com.walletmanager.app.utils.WalletPreference

class SplashActivity : AppCompatActivity() {

    private var isFirstTime: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        isFirstTime = WalletPreference.getBooleanPreference(this, WalletPreference.FIRST_RUN)

        if (isFirstTime) {
            CurrencyUtils.setDefaultCurrency(this)
            WalletPreference.setBooleanPreference(this, WalletPreference.FIRST_RUN, false)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}