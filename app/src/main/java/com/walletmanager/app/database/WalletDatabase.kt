package com.walletmanager.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.walletmanager.app.database.dao.ExpenseDao
import com.walletmanager.app.database.dao.IncomeDao
import com.walletmanager.app.database.dao.TransactionDao
import com.walletmanager.app.database.entities.DbExpense
import com.walletmanager.app.database.entities.DbIncome
import com.walletmanager.app.database.entities.DbTransaction

@Database(entities = [DbIncome::class, DbExpense::class, DbTransaction::class], version = 15, exportSchema = false)
abstract class WalletDatabase: RoomDatabase() {
    abstract val incomeDao: IncomeDao

    abstract val expenseDao: ExpenseDao

    abstract val transactionDao: TransactionDao

    companion object{
        @Volatile
        private var INSTANCE: WalletDatabase? = null

        fun getInstance(context: Context): WalletDatabase{
            synchronized(this){
                var  instance = INSTANCE
                if (instance == null){
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