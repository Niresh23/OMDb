package com.niresh23.omdb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.niresh23.omdb.data.SearchData

class MainViewModel : ViewModel() {
    private val _showDetailsLiveData = MutableLiveData<SearchData>()
    val showDetailsLiveData: LiveData<SearchData>
        get() = _showDetailsLiveData

    private val _openImdbWebSiteLiveData = MutableLiveData<String>()
    val openImdbWebSiteLiveData: LiveData<String>
        get() = _openImdbWebSiteLiveData

    fun showDetails(searchData: SearchData) {
        _showDetailsLiveData.value = searchData
    }

    fun openOnImdbWebSite(imdbId: String) {
        _openImdbWebSiteLiveData.value = imdbId
    }
}