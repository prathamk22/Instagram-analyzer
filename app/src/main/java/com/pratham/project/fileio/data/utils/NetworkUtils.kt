package com.pratham.project.fileio.data.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultWrapper.GenericError(
                        103,
                        "Not Connected To Internet"
                )
                is UnknownHostException -> ResultWrapper.GenericError(
                        101,
                        ErrorStatus.NO_CONNECTION
                )
                is SocketTimeoutException -> ResultWrapper.GenericError(
                        102,
                        ErrorStatus.TIMEOUT
                )
                is HttpException -> ResultWrapper.GenericError(
                        throwable.code(),
                        "HttpException"
                )
                else -> {
                    ResultWrapper.GenericError(
                            null,
                            throwable.localizedMessage ?: ""
                    )
                }
            }
        }
    }
}

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: String) : ResultWrapper<Nothing>()
}

object ErrorStatus{
    val TIMEOUT = ""
    val NO_CONNECTION = ""
}