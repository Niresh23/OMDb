package com.niresh23.omdb.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CommentDao {

    @Query("SELECT * FROM comment_table WHERE imdbId = :imdbId")
    suspend fun getCommentsByImdbId(imdbId: String): List<CommentEntity>

    @Query("SELECT COUNT(imdbId) AS comment_count FROM comment_table WHERE imdbId =:imdbId")
    suspend fun getCommentCountByImdbId(imdbId: String): Int

    @Query("SELECT imdbId, COUNT(imdbId) AS comment_count FROM comment_table")
    suspend fun getListPairCommentCountWithImdbId(): List<CommentCountEntity>

    @Insert
    suspend fun insertAll(vararg commentEntity: CommentEntity)

    @Insert
    suspend fun insert(commentEntity: CommentEntity)

    @Delete
    suspend fun delete(commentEntity: CommentEntity)
}