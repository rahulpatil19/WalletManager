package com.patilapps.walletmanager.database.dao

import androidx.room.*
import com.patilapps.walletmanager.database.entities.DbYear
import kotlinx.coroutines.flow.Flow

@Dao
interface YearDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertYear(dbYear: DbYear)

    @Query("SELECT * FROM table_year ORDER BY year")
    fun getAllYears(): Flow<List<DbYear>>

    @Update
    suspend fun updateTransactionYear(dbYear: DbYear)
}


fun List<DbYear>.asFilterList(): List<Int> {
    return map { dbYear ->
        dbYear.year
    }
}