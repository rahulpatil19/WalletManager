package com.patilapps.walletmanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.patilapps.walletmanager.database.entities.DbExpense

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insertExpense(dbExpense: DbExpense)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateExpense(dbExpense: DbExpense)

    @Query("SELECT * FROM table_expense")
    fun getAllExpense(): LiveData<List<DbExpense>>

    @Query("DELETE FROM table_expense")
    suspend fun deleteAllExpenses()
}