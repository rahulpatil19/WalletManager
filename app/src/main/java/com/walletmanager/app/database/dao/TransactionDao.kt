package com.walletmanager.app.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.walletmanager.app.database.entities.DbTransaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert
    suspend fun insertTransaction(dbTransaction: DbTransaction)

    @Query("SELECT * FROM table_all_transaction")
    fun getAllTransaction(): Flow<List<DbTransaction>>

    @Query("SELECT * FROM table_all_transaction")
    fun getTransactionByPage(): PagingSource<Int, DbTransaction>

    @Query("SELECT * FROM table_all_transaction ORDER BY date")
    fun getTransactionPageSortedByDate(): PagingSource<Int, DbTransaction>

    @Query("SELECT * FROM table_all_transaction WHERE month LIKE :month ORDER BY date")
    fun getTransactionByMonth(month: String): PagingSource<Int, DbTransaction>



    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTransaction(dbTransaction: DbTransaction)
}


