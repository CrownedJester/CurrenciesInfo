package com.crownedjester.soft.currenciesinfo.common

sealed class Response<T>(val data: T? = null, val message: String = "") {
    class Success<T>(data: T) : Response<T>(data)
    class Error<T>(message: String, data: T? = null) : Response<T>(data, message)
    class Empty<T>(data: T? = null, message: String): Response<T>(data, message)
}