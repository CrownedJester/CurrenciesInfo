package com.crownedjester.soft.currenciesinfo.domain.repository.local

import android.util.Log
import com.crownedjester.soft.currenciesinfo.domain.model.Currency
import java.io.*

class LocalRepositoryImpl : LocalRepository {

    override fun loadCache(file: File): List<Currency> {
        return try {
            val inputStream = ObjectInputStream(FileInputStream(file))
            var result: List<Currency>
            inputStream.use {
                result = it.readObject() as List<Currency>
                it.close()
            }
            result
        } catch (e: IOException) {
            Log.e("LocalRepo", "Error reading file")
            emptyList()
        }
    }

    override fun saveCache(data: List<Currency>, dir: String): Boolean {
        return try {
            val file = File(dir)
            val outputStream = ObjectOutputStream(FileOutputStream(file))
            outputStream.use {
                it.writeObject(data)
                it.flush()
                it.close()
            }

            true
        } catch (e: IOException) {
            Log.e("LocalRepo", "Error writing data")
            false
        }
    }


}