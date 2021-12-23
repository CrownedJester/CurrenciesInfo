package com.crownedjester.soft.currenciesinfo.representation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crownedjester.soft.currenciesinfo.common.Response
import com.crownedjester.soft.currenciesinfo.domain.use_case.get_currencies.GetCurrenciesData
import com.crownedjester.soft.currenciesinfo.representation.util.DateUtil.MODE_SEND
import com.crownedjester.soft.currenciesinfo.representation.util.DateUtil.getCurrentDate
import com.crownedjester.soft.currenciesinfo.representation.util.DateUtil.getTomorrowDate
import com.crownedjester.soft.currenciesinfo.representation.util.DateUtil.getYesterdayDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    private val getCurrenciesData: GetCurrenciesData
) : ViewModel() {

    val isTomorrowsDataNotExists = MutableLiveData(false)
    private val _todayCurrenciesData = MutableLiveData(CurrenciesState())
    val todayCurrenciesData: LiveData<CurrenciesState> = _todayCurrenciesData

    init {
        viewModelScope.launch {
            getCurrencies()
        }
    }

    private suspend fun getCurrencies() {
        combine(
            getCurrenciesData(getCurrentDate(MODE_SEND)),
            getCurrenciesData(getTomorrowDate(MODE_SEND)),
            getCurrenciesData(getYesterdayDate(MODE_SEND))
        ) { today, tomorrow, yesterday ->
            if (today is Response.Error) {
                Log.i("ViewModel", "Combine stop because of error occurred")
                return@combine today
            }

            if (tomorrow is Response.Empty) {
                Log.i("ViewModel", "Using yesterday's data")
                isTomorrowsDataNotExists.postValue(true)
                today.data?.mapIndexed { i, currency ->
                    currency.alternativeRate = yesterday.data?.get(i)?.rate!!
                }
            } else {
                isTomorrowsDataNotExists.postValue(false)
                Log.i("ViewModel", "Using tomorrow's data")
                today.data?.mapIndexed { i, currency ->
                    currency.alternativeRate = tomorrow.data?.get(i)?.rate!!
                }
            }

            today
        }.collectLatest { result ->
            when (result) {
                is Response.Success -> {
                    _todayCurrenciesData.value = CurrenciesState(data = result.data)
                    Log.i("ViewModel", result.data.toString())
                }
                is Response.Error -> {
                    _todayCurrenciesData.value = CurrenciesState(error = result.message)
                }
            }
        }
    }
}