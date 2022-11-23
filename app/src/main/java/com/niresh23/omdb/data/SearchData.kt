package com.niresh23.omdb.data

import androidx.room.ColumnInfo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SearchData(
    @SerializedName("Title")
    val title: String,

    @SerializedName("Year")
    val year: String,

    @ColumnInfo(name = "imdb_id")
    val imdbID: String,

    @SerializedName("Type")
    val type: String,

    @SerializedName("Poster")
    val poster: String,

    @Expose(serialize = false)
    var isFavorite: Boolean = false,

    @Expose(serialize = false)
    var commentCount: Int = 0
)
