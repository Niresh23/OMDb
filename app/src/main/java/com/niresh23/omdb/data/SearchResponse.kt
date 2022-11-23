package com.niresh23.omdb.data

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("Search")
    val search: List<SearchData>?
)
