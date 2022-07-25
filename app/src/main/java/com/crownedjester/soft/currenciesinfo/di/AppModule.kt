@file:Suppress("DEPRECATION")

package com.crownedjester.soft.currenciesinfo.di

import android.app.Application
import androidx.room.Room
import com.crownedjester.soft.currenciesinfo.common.Constants.BASE_URL
import com.crownedjester.soft.currenciesinfo.data.NBRBServiceApi
import com.crownedjester.soft.currenciesinfo.data.createClient
import com.crownedjester.soft.currenciesinfo.domain.data_source.CurrencyDatabase
import com.crownedjester.soft.currenciesinfo.domain.repository.local.LocalRepository
import com.crownedjester.soft.currenciesinfo.domain.repository.local.LocalRepositoryImpl
import com.crownedjester.soft.currenciesinfo.domain.repository.remote.RemoteServiceRepository
import com.crownedjester.soft.currenciesinfo.domain.repository.remote.RemoteServiceRepositoryImpl
import com.crownedjester.soft.currenciesinfo.domain.use_case.UseCases
import com.crownedjester.soft.currenciesinfo.domain.use_case.clear_cache.ClearCache
import com.crownedjester.soft.currenciesinfo.domain.use_case.get_currencies.GetCurrenciesData
import com.crownedjester.soft.currenciesinfo.domain.use_case.load_cache.LoadCache
import com.crownedjester.soft.currenciesinfo.domain.use_case.save_cache.SaveCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDatabase(application: Application): CurrencyDatabase =
        Room.databaseBuilder(
            application,
            CurrencyDatabase::class.java,
            CurrencyDatabase.DB_NAME
        ).build()


    @Provides
    @Singleton
    fun providesRemoteService(): NBRBServiceApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createClient())
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
            .create(NBRBServiceApi::class.java)

    @Provides
    @Singleton
    fun providesRemoteRepository(remoteApi: NBRBServiceApi): RemoteServiceRepository =
        RemoteServiceRepositoryImpl(remoteApi)

    @Provides
    @Singleton
    fun providesLocalRepository(currencyDatabase: CurrencyDatabase): LocalRepository =
        LocalRepositoryImpl(currencyDatabase.dao)

    @Provides
    @Singleton
    fun providesUseCases(
        localRepository: LocalRepository,
        remoteRepository: RemoteServiceRepository
    ): UseCases =
        UseCases(
            saveCache = SaveCache(localRepository),
            loadCache = LoadCache(localRepository),
            getCurrenciesData = GetCurrenciesData(remoteRepository),
            clearCache = ClearCache(localRepository)
        )
}