package com.patilapps.walletmanager.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_year")
data class DbYear(@PrimaryKey val year: Int)
