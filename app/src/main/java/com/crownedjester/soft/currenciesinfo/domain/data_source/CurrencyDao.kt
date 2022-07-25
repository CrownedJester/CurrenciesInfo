package com.crownedjester.soft.currenciesinfo.domain.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.crownedjester.soft.currenciesinfo.domain.model.CachedCurrency
import kotlinx.coroutines.flow.Flow


@Dao
interface CurrencyDao {

    @Query("select * from currency_table")
    fun retrieveCurrencies(): Flow<List<CachedCurrency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCurrencies(vararg currency: CachedCurrency)

    @Query("delete from currency_table")
    suspend fun clearCache(): Int

}