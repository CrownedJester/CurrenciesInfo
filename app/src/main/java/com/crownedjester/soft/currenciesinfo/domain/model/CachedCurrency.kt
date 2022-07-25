package com.crownedjester.soft.currenciesinfo.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_table")
data class CachedCurrency(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val numCode: Int,
    @ColumnInfo(name = "char_code")
    val charCode: String,
    @ColumnInfo(name = "tracking")
    val isTracking: Boolean,
    val position: Int
)
