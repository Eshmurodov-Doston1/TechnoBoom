package com.example.technoboom.utils

sealed class ResponseState<out T> {
    object Loading:ResponseState<Nothing>()
    data class Success<T>(val data:T):ResponseState<T>()
    data class Error(val errorCode: Int, val message: String? = null):ResponseState<Nothing>()
}