package com.crownedjester.soft.currenciesinfo.di

import com.crownedjester.soft.currenciesinfo.common.Constants.BASE_URL
import com.crownedjester.soft.currenciesinfo.data.NBRBServiceApi
import com.crownedjester.soft.currenciesinfo.data.createClient
import com.crownedjester.soft.currenciesinfo.domain.repository.local.LocalRepository
import com.crownedjester.soft.currenciesinfo.domain.repository.local.LocalRepositoryImpl
import com.crownedjester.soft.currenciesinfo.domain.repository.remote.RemoteServiceRepository
import com.crownedjester.soft.currenciesinfo.domain.repository.remote.RemoteServiceRepositoryImpl
import com.crownedjester.soft.currenciesinfo.domain.use_case.UseCases
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
    fun providesLocalRepository(): LocalRepository =
        LocalRepositoryImpl()

    @Provides
    @Singleton
    fun providesUseCases(
        localRepository: LocalRepository,
        remoteRepository: RemoteServiceRepository
    ): UseCases =
        UseCases(
            saveCache = SaveCache(localRepository),
            loadCache = LoadCache(localRepository),
            getCurrenciesData = GetCurrenciesData(remoteRepository)
        )
}