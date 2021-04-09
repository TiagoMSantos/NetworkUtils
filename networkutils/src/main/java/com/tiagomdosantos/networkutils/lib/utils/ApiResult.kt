package com.tiagomdosantos.networkutils.lib.utils

import retrofit2.Response

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val exception: Exception, val message: String = exception.localizedMessage.orEmpty()) : ApiResult<Nothing>()
}

fun <T : Any> handleError(response: Response<T>): ApiResult.Error {
    val error = ApiErrorUtils.parseError(response = response)
    return ApiResult.Error(exception = Exception(error))
}

fun <T : Any> handleSuccess(response: Response<T>): ApiResult<T> {
    return response.body()?.let { data ->
        ApiResult.Success(data = data)
    } ?: run {
        handleError(response = response)
    }
}
