package com.walletmanager.app.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.walletmanager.app.database.entities.DbExpense

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insertExpense(dbExpense: DbExpense)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateExpense(dbExpense: DbExpense)

    @Query("SELECT * FROM table_expense")
    fun getAllExpense(): LiveData<List<DbExpense>>
}