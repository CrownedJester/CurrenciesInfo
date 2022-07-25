package com.crownedjester.soft.currenciesinfo.domain.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.crownedjester.soft.currenciesinfo.domain.model.Currency

@Database(
    version = 1,
    entities = [Currency::class]
)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract val dao: CurrencyDao

    companion object {
        const val DB_NAME = "currency_db"
    }

}