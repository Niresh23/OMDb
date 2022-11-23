package com.niresh23.omdb.database

import androidx.room.ColumnInfo

data class CommentCountEntity(
    val imdbId: String,
    @ColumnInfo(name = "comment_count")
    val commentCount: Int
)
