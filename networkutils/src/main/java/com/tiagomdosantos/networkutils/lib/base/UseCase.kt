package com.tiagomdosantos.networkutils.lib.base

import com.tiagomdosantos.networkutils.lib.coroutines.CoroutineContextProvider
import com.tiagomdosantos.networkutils.lib.utils.ApiError
import com.tiagomdosantos.networkutils.lib.utils.handleCancellationException
import com.tiagomdosantos.networkutils.lib.utils.handleHttpException
import com.tiagomdosantos.networkutils.lib.utils.handleIOException
import com.tiagomdosantos.networkutils.lib.utils.handleSocketTimeOutException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.concurrent.CancellationException

abstract class UseCase<Type, in Params>(private val provider: CoroutineContextProvider) where Type : Any {

    abstract suspend fun run(params: Params): Type

    fun execute(
        scope: CoroutineScope,
        params: Params,
        success: (response: Type) -> Unit,
        failure: (error: ApiError) -> Unit
    ) {
        scope.launch(context = provider.io()) {
            try {
                val result = run(params = params)
                withContext(context = provider.main()) {
                    success(result)
                }
            } catch (cancellationException: CancellationException) {
                withContext(context = provider.main()) {
                    failure(handleCancellationException())
                }
            } catch (socketTimeoutException: SocketTimeoutException) {
                withContext(context = provider.main()) {
                    failure(handleSocketTimeOutException())
                }
            } catch (ioException: IOException) {
                withContext(context = provider.main()) {
                    failure(handleIOException())
                }
            } catch (httpException: HttpException) {
                withContext(context = provider.main()) {
                    failure(handleHttpException(httpException = httpException))
                }
            }
        }
    }
}
