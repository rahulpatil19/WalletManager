package com.walletmanager.app.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.walletmanager.app.database.entities.DbIncome

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

}