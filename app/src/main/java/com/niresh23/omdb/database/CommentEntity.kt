package com.niresh23.omdb.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comment_table")
data class CommentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val imdbId: String,
    val comment: String
)
