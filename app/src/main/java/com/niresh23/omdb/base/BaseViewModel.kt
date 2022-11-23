package com.niresh23.omdb.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel() {
    private val _errorLiveData = MutableLiveData<AppError>()
    val errorLiveData: LiveData<AppError>
        get() = _errorLiveData

    fun <T> handleError(errorResult: ResultWrapper<T>) {
        when(errorResult) {

            is ResultWrapper.GenericError -> {
                if (errorResult.code == 404) {
                    _errorLiveData.postValue(AppError.PageNotFound)
                } else {
                    _errorLiveData.postValue(AppError.Generic(errorResult.code, errorResult.error?.message))
                }

            }

            is ResultWrapper.NetworkError -> {
                _errorLiveData.postValue(AppError.NoInternetConnection)
            }

            else -> {}
        }
    }
}