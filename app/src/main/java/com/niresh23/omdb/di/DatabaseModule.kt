package com.niresh23.omdb.di

import android.content.Context
import androidx.room.Room
import com.niresh23.omdb.database.AppDatabase
import com.niresh23.omdb.database.CommentDao
import com.niresh23.omdb.database.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun getCommentDao(database: AppDatabase): CommentDao {
        return database.commentDao()
    }

    @Provides
    fun getFavoriteDao(database: AppDatabase): FavoriteDao {
        return database.favoriteDao()
    }

    @Provides
    fun getAppDatabase(@ApplicationContext applicationContext: Context): AppDatabase =
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "favorite_movies"
        ).build()

}