package com.crownedjester.soft.currenciesinfo.domain.repository

import com.crownedjester.soft.currenciesinfo.data.NBRBServiceApi
import com.crownedjester.soft.currenciesinfo.data.RemoteServiceRepository
import com.crownedjester.soft.currenciesinfo.data.model.CurrencyDto
import javax.inject.Inject

class RemoteServiceRepositoryImpl @Inject constructor(private val remoteServiceApi: NBRBServiceApi) :
    RemoteServiceRepository {

    override suspend fun getCurrenciesData(date: String): List<CurrencyDto> {
        return remoteServiceApi.getCurrenciesData(date)
    }


}