package com.crownedjester.soft.currenciesinfo.representation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crownedjester.soft.currenciesinfo.common.Constants.DEFAULT_CURRENCIES
import com.crownedjester.soft.currenciesinfo.common.Response
import com.crownedjester.soft.currenciesinfo.domain.model.CachedCurrency
import com.crownedjester.soft.currenciesinfo.domain.model.Currency
import com.crownedjester.soft.currenciesinfo.domain.use_case.UseCases
import com.crownedjester.soft.currenciesinfo.representation.util.DateUtil.MODE_SEND
import com.crownedjester.soft.currenciesinfo.representation.util.DateUtil.TOMORROW_CODE
import com.crownedjester.soft.currenciesinfo.representation.util.DateUtil.YESTERDAY_CODE
import com.crownedjester.soft.currenciesinfo.representation.util.DateUtil.getAlternativeDate
import com.crownedjester.soft.currenciesinfo.representation.util.DateUtil.getCurrentDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ViewModel"

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _isTomorrowDataExistsStateFlow = MutableStateFlow(true)
    val isTomorrowsDataExistsStateFlow: StateFlow<Boolean> = _isTomorrowDataExistsStateFlow

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
            useCases.getCurrenciesData(getAlternativeDate(MODE_SEND, TOMORROW_CODE)),
            useCases.getCurrenciesData(getAlternativeDate(MODE_SEND, YESTERDAY_CODE)),
            useCases.loadCache()
        ) { today, tomorrow, yesterday, cachedCurrencies ->
            if (today is Response.Error) {
                Log.e("ViewModel", "Combine stop because of error occurred")
                return@combine today
            }

            Log.i("ViewModel", "Today's data loaded -> ${today.data?.isNotEmpty()}")
            Log.i("ViewModel", "Tomorrow's data loaded -> ${tomorrow.data?.isNotEmpty()}")
            Log.i("ViewModel", "Yesterday's data loaded -> ${yesterday.data?.isNotEmpty()}")

            if (tomorrow is Response.Empty) {

                Log.d("ViewModel", "Using yesterday's data")

                _isTomorrowDataExistsStateFlow.emit(false)

                today.data?.forEachIndexed { i, currency ->

                    currency.apply {
                        position = i

                        applyCachedState(cachedCurrencies)

                        alternativeRate = yesterday.data?.get(i)?.rate!!
                    }


                }

            } else {

                Log.i("ViewModel", "Using tomorrow's data")

                _isTomorrowDataExistsStateFlow.emit(true)

                today.data?.forEachIndexed { i, currency ->

                    currency.apply {
                        position = i

                        applyCachedState(cachedCurrencies)

                        alternativeRate = tomorrow.data?.get(i)?.rate!!

                    }
                }
            }

            today
        }.collectLatest { result ->

            when (result) {
                is Response.Success -> {
                    _todayCurrenciesState.value = CurrenciesState(data = result.data)

                }
                is Response.Error -> {
                    _todayCurrenciesState.value = CurrenciesState(error = result.message)
                }
                else -> {}
            }

        }
    }

    fun saveCache(data: List<Currency>) {
        viewModelScope.launch {
            useCases.saveCache(data)

            useCases.loadCache().collectLatest {

                if (it.size > _todayCurrenciesState.value.data?.size!!) {

                    clearCache()
                    Log.d(TAG, "Cache cleared because of many redundant notes")

                } else {

                    useCases.saveCache(data)
                    Log.d(TAG, "Notes were re-populated")

                }

            }

        }
    }

    private fun clearCache() {
        viewModelScope.launch {
            useCases.clearCache()
        }
    }

    private fun Currency.applyCachedState(cachedData: List<CachedCurrency>) {
        if (cachedData.isEmpty()) {
            if (this.charCode in DEFAULT_CURRENCIES) this.isTracking = true
        } else {
            cachedData.forEach { cachedCurrency ->
                if (this.charCode == cachedCurrency.charCode) {
                    this.apply {
                        this.position = cachedCurrency.position

                        this.isTracking = cachedCurrency.isTracking
                    }
                }
            }
        }
    }

}