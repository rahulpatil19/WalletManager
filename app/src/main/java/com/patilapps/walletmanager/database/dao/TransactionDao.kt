package com.patilapps.walletmanager.database.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.patilapps.walletmanager.database.entities.DbTransaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert
    suspend fun insertTransaction(dbTransaction: DbTransaction)

    // using this at home screen to get 10 recent transactions
    @Query("SELECT * FROM table_all_transaction ORDER BY date LIMIT 10")
    fun getTenRecentTransactions(): Flow<List<DbTransaction>>

    // default query to get all the transaction by page
    @Query("SELECT * FROM table_all_transaction ORDER BY date")
    fun getTransactionPageSortedByDate(): PagingSource<Int, DbTransaction>

    // Used in statistics filter by year
    @Query("SELECT * FROM table_all_transaction WHERE year LIKE :year")
    fun getTransactionByYear(year: Int): Flow<List<DbTransaction>>

    // Used in wallet filter by month
    @Query("SELECT * FROM table_all_transaction WHERE month LIKE :month AND year LIKE :year ORDER BY date")
    fun getTransactionByMonth(month: String, year: Int): Flow<List<DbTransaction>>

    // used in wallet filter category
    @Query("SELECT * FROM table_all_transaction WHERE category LIKE :category ORDER BY date")
    fun getTransactionByCategory(category: String): PagingSource<Int, DbTransaction>

    @Update
    suspend fun updateTransaction(dbTransaction: DbTransaction)

    @Delete
    suspend fun deleteTransaction(dbTransaction: DbTransaction)

    @Query("DELETE FROM table_all_transaction")
    suspend fun deleteAllTransactions()

    @Query("SELECT * FROM table_all_transaction WHERE title LIKE '%' || :keyword || '%' OR category LIKE '%' || :keyword || '%'")
    fun getTransactionByKeyword(keyword: String): Flow<List<DbTransaction>>

}