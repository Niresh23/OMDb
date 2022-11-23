package com.niresh23.omdb.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.niresh23.omdb.base.BaseViewModel
import com.niresh23.omdb.base.ResultWrapper
import com.niresh23.omdb.base.zip
import com.niresh23.omdb.data.SearchData
import com.niresh23.omdb.repository.CommentRepository
import com.niresh23.omdb.repository.FavoriteRepository
import com.niresh23.omdb.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val favoriteRepository: FavoriteRepository,
    private val commentRepository: CommentRepository
) : BaseViewModel() {
    private val _searchDataLiveData = MutableLiveData<List<SearchData>>()
    val searchDataLiveData: LiveData<List<SearchData>>
        get() = _searchDataLiveData

    fun searchMovie(title: String, page: String) {
        viewModelScope.launch {
            val response = repository.searchMovies(title, page)
            val favoriteResult = favoriteRepository.getAllFavoriteEntities()
            val imdbIdAndCommentCountListResult = commentRepository.getListPairCommentCountWithImdbId()
            val result: ResultWrapper<List<SearchData>?> = response.zip(favoriteResult) { searchResponse, favoriteList ->
                val favoriteIdsList: List<String> = favoriteList.map { it.imdbId }
                searchResponse.search?.map { searchData ->
                    searchData.isFavorite = favoriteIdsList.contains(searchData.imdbID)
                }

                ResultWrapper.Success(searchResponse.search)
            }.zip(imdbIdAndCommentCountListResult) { searchDataList, imdbIdAndCommentCountPairList ->
                searchDataList?.zip(imdbIdAndCommentCountPairList) { searchData, pair ->
                    if (searchData.imdbID == pair.first) {
                        searchData.commentCount = pair.second
                    }
                    searchData
                }
                ResultWrapper.Success(searchDataList)
            }

            when(result) {
                is ResultWrapper.Success -> {
                    _searchDataLiveData.postValue(result.data ?: emptyList())
                }

                else -> handleError(result)
            }
        }
    }
}