package com.niresh23.omdb.base

sealed class AppError {
    data class Generic(val code: Int?, val message: String?): AppError()
    object NoInternetConnection: AppError()
    object PageNotFound: AppError()
}
