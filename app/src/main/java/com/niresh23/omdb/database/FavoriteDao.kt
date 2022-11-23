package com.niresh23.omdb.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_table")
    suspend fun getAll(): List<FavoriteEntity>

    @Query("SELECT EXISTS (SELECT 1 FROM favorite_table WHERE id = :id)")
    fun contains(id: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg favoriteEntity: FavoriteEntity)

    @Insert
    suspend fun insert(favoriteEntity: FavoriteEntity)

    @Delete
    suspend fun delete(favoriteEntity: FavoriteEntity)
}