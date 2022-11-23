package com.niresh23.omdb.utils

import com.niresh23.omdb.base.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.sql.SQLException

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    val response = throwable.response()
                    val errorBody = response?.errorBody()?.string()

                    ResultWrapper.GenericError(code, throwable)
                }

                is SQLException -> {
                    ResultWrapper.DatabaseError(throwable.message ?: "Database error")
                }

                else -> {
                    ResultWrapper.GenericError(null, null)
                }
            }
        }
    }
}