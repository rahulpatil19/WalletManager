package com.walletmanager.app.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_month")
data class DbMonth(@PrimaryKey val month: String)