package com.niresh23.omdb.di

import com.niresh23.omdb.database.CommentDao
import com.niresh23.omdb.database.FavoriteDao
import com.niresh23.omdb.repository.CommentRepository
import com.niresh23.omdb.repository.FavoriteRepository
import com.niresh23.omdb.repository.MovieRepository
import com.niresh23.omdb.service.OmdbService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun getMovieRepository(apiService: OmdbService): MovieRepository {
        return MovieRepository(apiService)
    }

    @Provides
    fun getFavoriteRepository(favoriteDao: FavoriteDao): FavoriteRepository {
        return FavoriteRepository(favoriteDao)
    }

    @Provides
    fun getCommentRepository(commentDao: CommentDao): CommentRepository {
        return CommentRepository(commentDao)
    }
}