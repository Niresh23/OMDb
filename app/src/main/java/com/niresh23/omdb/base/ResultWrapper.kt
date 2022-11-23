package com.niresh23.omdb.base

sealed class ResultWrapper<out T> {
    data class Success<out T>(val data: T): ResultWrapper<T>()
    data class GenericError(val code: Int?, val error: Exception?): ResultWrapper<Nothing>()
    data class DatabaseError(val message: String): ResultWrapper<Nothing>()
    object NetworkError: ResultWrapper<Nothing>()
}

fun <T, V, R> ResultWrapper<T>.zip(result: ResultWrapper<V>, transform: (a: T, b: V) -> ResultWrapper<R>): ResultWrapper<R> {

    return when(this) {
        is ResultWrapper.Success -> {
            return when(result) {
                is ResultWrapper.Success -> {
                    if (this.data == null || result.data == null) {
                        return ResultWrapper.GenericError(null, IllegalStateException("No Data"))
                    } else {
                        transform.invoke(this.data, result.data)
                    }
                }

                is ResultWrapper.GenericError -> result

                is ResultWrapper.NetworkError -> result

                is ResultWrapper.DatabaseError -> result
            }
        }

        is ResultWrapper.GenericError -> this

        is ResultWrapper.NetworkError -> this

        is ResultWrapper.DatabaseError -> this

    }
}
