package com.niresh23.omdb.service

import com.niresh23.omdb.data.Movie
import com.niresh23.omdb.data.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbService {
    @GET("/")
    suspend fun searchMovie(@Query("s") title: String, @Query("page") pagesNumber: String): SearchResponse

    suspend fun getMovieInfo(@Query("i") imdbId: String): Movie
}