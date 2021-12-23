package com.crownedjester.soft.currenciesinfo.domain.use_case.get_currencies

import com.crownedjester.soft.currenciesinfo.common.Response
import com.crownedjester.soft.currenciesinfo.data.model.toCurrency
import com.crownedjester.soft.currenciesinfo.domain.model.Currency
import com.crownedjester.soft.currenciesinfo.domain.repository.RemoteServiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCurrenciesData @Inject constructor(private val repository: RemoteServiceRepository) {

    operator fun invoke(date: String): Flow<Response<List<Currency>>> = flow {
        try {
            val result =
                repository.getCurrenciesData(date).map { currencyDto -> currencyDto.toCurrency() }
            if (result.isEmpty()) {
                emit(Response.Empty(message = "Response is empty"))
            } else {
                emit(Response.Success(result))
            }
        } catch (e: HttpException) {
            emit(Response.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Response.Error("Check Host state. It may not work properly"))
        }
    }
}