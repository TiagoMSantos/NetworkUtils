package com.tiagomdosantos.networkutils.lib.utils

import com.tiagomdosantos.networkutils.lib.objects.Constants.BAD_REQUEST
import com.tiagomdosantos.networkutils.lib.objects.Constants.CONFLICT
import com.tiagomdosantos.networkutils.lib.objects.Constants.FORBIDDEN
import com.tiagomdosantos.networkutils.lib.objects.Constants.INTERNAL_SERVER_ERROR
import com.tiagomdosantos.networkutils.lib.objects.Constants.METHOD_NOT_ALLOWED
import com.tiagomdosantos.networkutils.lib.objects.Constants.NOT_FOUND
import com.tiagomdosantos.networkutils.lib.objects.Constants.UNAUTHORIZED
import retrofit2.HttpException

data class ApiError(var code: Int, var status: ErrorStatus) {
    enum class ErrorStatus {
        BAD_REQUEST,
        UNAUTHORIZED,
        FORBIDDEN,
        NOT_FOUND,
        METHOD_NOT_ALLOWED,
        CONFLICT,
        INTERNAL_SERVER_ERROR,
        TIMEOUT,
        NO_CONNECTION,
        UNKNOWN_ERROR
    }
}

fun handleSocketTimeOutException(): ApiError {
    return ApiError(
        code = 0,
        status = ApiError.ErrorStatus.TIMEOUT
    )
}

fun handleIOException(): ApiError {
    return ApiError(
        code = 0,
        status = ApiError.ErrorStatus.NO_CONNECTION
    )
}

fun handleCancellationException(): ApiError {
    return ApiError(
        code = 0,
        status = ApiError.ErrorStatus.UNKNOWN_ERROR
    )
}

fun handleHttpException(httpException: HttpException): ApiError {
    return when (val code = httpException.code()) {
        BAD_REQUEST -> ApiError(
            code = code,
            status = ApiError.ErrorStatus.BAD_REQUEST
        )
        UNAUTHORIZED -> ApiError(
            code = code,
            status = ApiError.ErrorStatus.UNAUTHORIZED
        )
        FORBIDDEN -> ApiError(
            code = code,
            status = ApiError.ErrorStatus.FORBIDDEN
        )
        NOT_FOUND -> ApiError(
            code = code,
            status = ApiError.ErrorStatus.NOT_FOUND
        )
        METHOD_NOT_ALLOWED -> ApiError(
            code = code,
            status = ApiError.ErrorStatus.METHOD_NOT_ALLOWED
        )
        CONFLICT -> ApiError(
            code = code,
            status = ApiError.ErrorStatus.CONFLICT
        )
        INTERNAL_SERVER_ERROR -> ApiError(
            code = code,
            status = ApiError.ErrorStatus.INTERNAL_SERVER_ERROR
        )
        else -> ApiError(
            code = 0,
            status = ApiError.ErrorStatus.UNKNOWN_ERROR
        )
    }
}
