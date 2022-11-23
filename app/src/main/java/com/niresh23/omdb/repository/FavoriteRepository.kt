package com.niresh23.omdb.repository

import com.niresh23.omdb.base.ResultWrapper
import com.niresh23.omdb.database.FavoriteDao
import com.niresh23.omdb.database.FavoriteEntity
import com.niresh23.omdb.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


class FavoriteRepository @Inject constructor(private val favoriteDao: FavoriteDao) {

    private val dispatcher = Dispatchers.IO

    suspend fun getAllFavoriteEntities(): ResultWrapper<List<FavoriteEntity>> =
        safeApiCall(dispatcher) {
            favoriteDao.getAll()
        }

    suspend fun insertFavorite(imdbId: String): ResultWrapper<Unit> =
        safeApiCall(dispatcher) {
            favoriteDao.insert(FavoriteEntity(imdbId))
        }

    suspend fun contains(imdbId: String): ResultWrapper<Boolean> =
        safeApiCall(dispatcher) {
            favoriteDao.contains(imdbId)
        }

    suspend fun delete(imdbId: String): ResultWrapper<Unit> =
        safeApiCall(dispatcher) {
            favoriteDao.delete(FavoriteEntity(imdbId))
        }

}