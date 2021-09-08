package com.patilapps.walletmanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.patilapps.walletmanager.database.dao.*
import com.patilapps.walletmanager.database.entities.*

@Database(entities = [DbIncome::class, DbExpense::class, DbTransaction::class, DbYear::class, DbMonth::class],
    version = 18,
    exportSchema = false)
abstract class WalletDatabase : RoomDatabase() {
    abstract val incomeDao: IncomeDao

    abstract val expenseDao: ExpenseDao

    abstract val transactionDao: TransactionDao

    abstract val yearDao: YearDao

    abstract val monthDao: MonthDao

    companion object {
        @Volatile
        private var INSTANCE: WalletDatabase? = null

        fun getInstance(context: Context): WalletDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        WalletDatabase::class.java,
                        "wallet_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}