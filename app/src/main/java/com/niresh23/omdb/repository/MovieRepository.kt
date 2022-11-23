package com.niresh23.omdb.repository

import com.niresh23.omdb.base.ResultWrapper
import com.niresh23.omdb.data.Movie
import com.niresh23.omdb.data.SearchResponse
import com.niresh23.omdb.utils.safeApiCall
import com.niresh23.omdb.service.OmdbService
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiService: OmdbService
) {
    private val dispatcher = Dispatchers.IO

    suspend fun searchMovies(title: String, pages: String): ResultWrapper<SearchResponse> =
        safeApiCall(dispatcher) {
            apiService.searchMovie(title, pages)
        }

    suspend fun getMovieInfo(imdbId: String): ResultWrapper<Movie> =
        safeApiCall(dispatcher) {
            apiService.getMovieInfo(imdbId)
        }
}