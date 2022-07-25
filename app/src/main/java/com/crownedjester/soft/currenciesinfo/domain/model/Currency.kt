package com.crownedjester.soft.currenciesinfo.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "currency_table")
data class Currency(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val numCode: Int,
    @ColumnInfo(name = "char_code")
    val charCode: String,

    @Ignore val scale: Int,
    @Ignore val name: String,
    @Ignore val rate: Double,

    @ColumnInfo(name = "tracking")
    var isTracking: Boolean = false,
    @Ignore var alternativeRate: Double = 0.0,
    var position: Int = 0
)