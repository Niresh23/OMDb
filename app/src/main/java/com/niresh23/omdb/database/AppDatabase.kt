package com.niresh23.omdb.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteEntity::class, CommentEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun commentDao(): CommentDao
}