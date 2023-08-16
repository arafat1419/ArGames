package com.arafat1419.argames.core.data.network.api

import com.arafat1419.argames.core.vo.ErrorData

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val error: ErrorData) : ApiResponse<Nothing>()
}
