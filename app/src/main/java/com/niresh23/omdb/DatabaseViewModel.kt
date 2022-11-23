package com.niresh23.omdb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.niresh23.omdb.base.BaseViewModel
import com.niresh23.omdb.base.ResultWrapper
import com.niresh23.omdb.data.SearchData
import com.niresh23.omdb.repository.CommentRepository
import com.niresh23.omdb.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import hilt_aggregated_deps._com_niresh23_omdb_DatabaseViewModel_HiltModules_BindsModule
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val commentRepository: CommentRepository
    ): BaseViewModel() {
    private val _addedInFavoriteLiveData = MutableLiveData<SearchData>()
    val addedInFavoriteLiveData: LiveData<SearchData>
        get() = _addedInFavoriteLiveData

    private val _commentCountLiveData = MutableLiveData<Pair<String, Int>>()
    val commentCountLiveData: LiveData<Pair<String, Int>>
        get() = _commentCountLiveData

    fun onLikeClicked(searchData: SearchData) {
        viewModelScope.launch {
            val result = if (searchData.isFavorite) {
                favoriteRepository.delete(searchData.imdbID)
            } else {
                favoriteRepository.insertFavorite(searchData.imdbID)
            }

            when (result) {
                is ResultWrapper.Success -> {
                    _addedInFavoriteLiveData.postValue(
                        searchData.apply {
                            isFavorite = !searchData.isFavorite
                        }
                    )
                }

                else -> {
                    handleError(result)
                }
            }
        }
    }

    fun saveComment(imdbId: String, comment: String) {
        viewModelScope.launch {
            when(val result = commentRepository.saveComment(imdbId, comment)) {
                is ResultWrapper.Success -> {
                    getCommentCount(imdbId)
                }

                else -> handleError(result)
            }
        }
    }

    fun getCommentCount(imdbId: String) {
        viewModelScope.launch {
            when(val result = commentRepository.getCommentCountByImdbId(imdbId)) {
                is ResultWrapper.Success -> {
                    _commentCountLiveData.postValue(imdbId to result.data)
                }

                else -> handleError(result)
            }
        }
    }
}