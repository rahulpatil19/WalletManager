package com.patilapps.walletmanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.patilapps.walletmanager.database.entities.DbIncome

@Dao
interface IncomeDao {

    @Insert
    suspend fun insertIncome(income: DbIncome)

    @Query("SELECT * FROM table_income")
    fun getAllIncome(): LiveData<List<DbIncome>>

    @Query("SELECT * FROM table_income WHERE date")
    fun getIncomeByDate(): LiveData<List<DbIncome>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateIncome(dbIncome: DbIncome)

    @Query("DELETE FROM table_income")
    suspend fun deleteAllIncomes()
}