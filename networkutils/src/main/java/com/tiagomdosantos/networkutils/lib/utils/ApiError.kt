package com.tiagomdosantos.networkutils.lib.utils

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

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

fun traceErrorException(throwable: Throwable?): ApiError {
    return when (throwable) {
        is HttpException -> {
            when (throwable.code()) {
                400 -> ApiError(
                    code = throwable.code(),
                    status = ApiError.ErrorStatus.BAD_REQUEST
                )
                401 -> ApiError(
                    code = throwable.code(),
                    status = ApiError.ErrorStatus.UNAUTHORIZED
                )
                403 -> ApiError(
                    code = throwable.code(),
                    status = ApiError.ErrorStatus.FORBIDDEN
                )
                404 -> ApiError(
                    code = throwable.code(),
                    status = ApiError.ErrorStatus.NOT_FOUND
                )
                405 -> ApiError(
                    code = throwable.code(),
                    status = ApiError.ErrorStatus.METHOD_NOT_ALLOWED
                )
                409 -> ApiError(
                    code = throwable.code(),
                    status = ApiError.ErrorStatus.CONFLICT
                )
                500 -> ApiError(
                    code = throwable.code(),
                    status = ApiError.ErrorStatus.INTERNAL_SERVER_ERROR
                )
                else -> ApiError(
                    code = 0,
                    status = ApiError.ErrorStatus.UNKNOWN_ERROR
                )
            }
        }

        is SocketTimeoutException -> {
            ApiError(
                code = 0,
                status = ApiError.ErrorStatus.TIMEOUT
            )
        }

        is IOException -> {
            ApiError(
                code = 0,
                status = ApiError.ErrorStatus.NO_CONNECTION
            )
        }

        else -> ApiError(code = 0, status = ApiError.ErrorStatus.UNKNOWN_ERROR)
    }
}
