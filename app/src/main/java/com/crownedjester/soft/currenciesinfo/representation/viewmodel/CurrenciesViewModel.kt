package com.crownedjester.soft.currenciesinfo.representation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crownedjester.soft.currenciesinfo.common.Response
import com.crownedjester.soft.currenciesinfo.domain.use_case.UseCases
import com.crownedjester.soft.currenciesinfo.representation.util.DateUtil.MODE_SEND
import com.crownedjester.soft.currenciesinfo.representation.util.DateUtil.getCurrentDate
import com.crownedjester.soft.currenciesinfo.representation.util.DateUtil.getTomorrowDate
import com.crownedjester.soft.currenciesinfo.representation.util.DateUtil.getYesterdayDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    val isTomorrowsDataNotExists = MutableLiveData(false)
    private val _todayCurrenciesState = MutableStateFlow(CurrenciesState())
    val todayCurrenciesState: StateFlow<CurrenciesState> = _todayCurrenciesState

    init {
        viewModelScope.launch {
            getCurrencies()
        }
    }

    private suspend fun getCurrencies() {
        Log.i("ViewModel", "Flows running")
        combine(
            useCases.getCurrenciesData(getCurrentDate(MODE_SEND)),
            useCases.getCurrenciesData(getTomorrowDate(MODE_SEND)),
            useCases.getCurrenciesData(getYesterdayDate(MODE_SEND))
        ) { today, tomorrow, yesterday ->
            if (today is Response.Error) {
                Log.i("ViewModel", "Combine stop because of error occurred")
                return@combine today
            }

            if (tomorrow is Response.Empty) {
                Log.i("ViewModel", "Using yesterday's data")
                isTomorrowsDataNotExists.value = true
                Log.i("ViewModel", "${isTomorrowsDataNotExists.value}")
                today.data?.mapIndexed { i, currency ->
                    currency.alternativeRate = yesterday.data?.get(i)?.rate!!
                    currency.position = i
                }
            } else {
                isTomorrowsDataNotExists.postValue(false)
                Log.i("ViewModel", "Using tomorrow's data")
                today.data?.mapIndexed { i, currency ->
                    currency.alternativeRate = tomorrow.data?.get(i)?.rate!!
                    currency.position = i
                }
            }

            today
        }.collectLatest { result ->
            when (result) {
                is Response.Success -> {
                    _todayCurrenciesState.value = CurrenciesState(data = result.data)

                    Log.i("ViewModel", result.data.toString())
                }
                is Response.Error -> {
                    _todayCurrenciesState.value = CurrenciesState(error = result.message)
                }
            }
        }
    }

}