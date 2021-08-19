package com.walletmanager.app.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.walletmanager.app.database.entities.DbTransaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert
    suspend fun insertTransaction(dbTransaction: DbTransaction)

    // using this at home screen to get 10 recent transactions
    @Query("SELECT * FROM table_all_transaction")
    fun getAllTransaction(): Flow<List<DbTransaction>>

    // Used in wallet filter by year
    @Query("SELECT * FROM table_all_transaction WHERE year LIKE :year")
    fun getTransactionByYear(year: Int): Flow<List<DbTransaction>>

    @Query("SELECT * FROM table_all_transaction")
    fun getTransactionByPage(): PagingSource<Int, DbTransaction>

    // default query to get all the transaction by page
    @Query("SELECT * FROM table_all_transaction ORDER BY date")
    fun getTransactionPageSortedByDate(): PagingSource<Int, DbTransaction>

    // Used in wallet filter by month
    @Query("SELECT * FROM table_all_transaction WHERE month LIKE :month ORDER BY date")
    fun getTransactionByMonth(month: String): PagingSource<Int, DbTransaction>

    // used in wallet filter category
    @Query("SELECT * FROM table_all_transaction WHERE category LIKE :category ORDER BY date")
    fun getTransactionByCategory(category: String): PagingSource<Int, DbTransaction>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTransaction(dbTransaction: DbTransaction)
}