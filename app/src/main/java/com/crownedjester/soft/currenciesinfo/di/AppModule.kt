package com.crownedjester.soft.currenciesinfo.di

import com.crownedjester.soft.currenciesinfo.common.Constants.BASE_URL
import com.crownedjester.soft.currenciesinfo.data.NBRBServiceApi
import com.crownedjester.soft.currenciesinfo.data.createClient
import com.crownedjester.soft.currenciesinfo.domain.repository.RemoteServiceRepository
import com.crownedjester.soft.currenciesinfo.domain.repository.RemoteServiceRepositoryImpl
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
}