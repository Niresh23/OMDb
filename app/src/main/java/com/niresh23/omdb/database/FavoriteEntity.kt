package com.niresh23.omdb.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class FavoriteEntity (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val imdbId: String,
)