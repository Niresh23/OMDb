package com.niresh23.omdb.repository

import com.niresh23.omdb.base.ResultWrapper
import com.niresh23.omdb.database.CommentDao
import com.niresh23.omdb.database.CommentEntity
import com.niresh23.omdb.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CommentRepository @Inject constructor(private val commentDao: CommentDao) {
    private val dispatcher = Dispatchers.IO

    suspend fun getCommentsByImdbId(imdbId: String): ResultWrapper<List<String>> =
        safeApiCall(dispatcher) {
            commentDao.getCommentsByImdbId(imdbId).map { it.comment }
        }

    suspend fun saveComment(imdbId: String, comment: String): ResultWrapper<Unit> =
        safeApiCall(dispatcher) {
            commentDao.insert(CommentEntity(imdbId = imdbId, comment = comment))
        }

    suspend fun getCommentCountByImdbId(imdbId: String): ResultWrapper<Int> =
        safeApiCall(dispatcher) {
            commentDao.getCommentCountByImdbId(imdbId)
        }

    suspend fun getListPairCommentCountWithImdbId(): ResultWrapper<List<Pair<String, Int>>> =
        safeApiCall(dispatcher) {
            commentDao.getListPairCommentCountWithImdbId().map { it.imdbId to it.commentCount }
        }
}