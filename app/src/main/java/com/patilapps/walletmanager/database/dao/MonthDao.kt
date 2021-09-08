package com.patilapps.walletmanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.patilapps.walletmanager.database.entities.DbMonth

@Dao
interface MonthDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonthWithYear(monthAndYear: DbMonth)

    @Query("SELECT * FROM TABLE_MONTH")
    fun getAllMonthWithYear(): LiveData<List<DbMonth>>
}

fun List<DbMonth>.asFilterList(): List<String>{
    return map { dbMonth ->
        dbMonth.month
    }
}