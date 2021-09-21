package com.patilapps.walletmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.patilapps.walletmanager.utils.CurrencyUtils
import com.patilapps.walletmanager.utils.WalletPreference
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private var isFirstTime: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        isFirstTime = WalletPreference.getBooleanPreference(this, WalletPreference.FIRST_RUN)

        if (isFirstTime) {
            CurrencyUtils.setDefaultCurrency(this@SplashActivity)
            WalletPreference.setBooleanPreference(this@SplashActivity,
                WalletPreference.FIRST_RUN,
                false)
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}