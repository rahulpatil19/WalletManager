package com.walletmanager.app.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.walletmanager.app.database.entities.DbYear
import kotlinx.coroutines.flow.Flow

@Dao
interface YearDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertYear(dbYear: DbYear)

    @Query("SELECT * FROM table_year ORDER BY year")
    fun getAllYears(): Flow<List<DbYear>>
}


fun List<DbYear>.asFilterList(): List<Int> {
    return map { dbYear ->
        dbYear.year
    }
}