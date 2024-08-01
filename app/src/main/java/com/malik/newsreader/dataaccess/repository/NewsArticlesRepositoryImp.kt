package com.malik.newsreader.dataaccess.repository

import androidx.annotation.WorkerThread
import com.malik.newsreader.common.noNetworkErrorMessage
import com.malik.newsreader.dataaccess.DataState
import com.malik.newsreader.dataaccess.models.NewsArticlesResponse
import com.malik.newsreader.dataaccess.remote.ApiInterface
import com.malik.newsreader.dataaccess.remote.message
import com.malik.newsreader.dataaccess.remote.onErrorSuspend
import com.malik.newsreader.dataaccess.remote.onExceptionSuspend
import com.malik.newsreader.dataaccess.remote.onSuccessSuspend
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

/**
 * The [NewsArticlesRepositoryImp].kt is an implementation of [NewsArticlesRepository]
 * @author Malik Dawar, malikdawar@hotmail.com
 */
class NewsArticlesRepositoryImp @Inject constructor(
    private val apiService: ApiInterface
) : NewsArticlesRepository {
    @WorkerThread
    override suspend fun getAvailableNewsArticles(page: Int): Flow<DataState<NewsArticlesResponse>> {
        return flow {
            apiService.loadNewsArticles(
                query = "tech",
                sortBy = "popularity",
                pageSize = 10,
                page = page
            ).apply {
                this.onSuccessSuspend {
                    data?.let {
                        emit(DataState.Success(it))
                    }
                }
                // handle the case when the API request gets an error response.
                // e.g. internal server error.
            }.onErrorSuspend {
                emit(DataState.Error<NewsArticlesResponse>(message()))
                // handle the case when the API request gets an exception response.
                // e.g. network connection error.
            }.onExceptionSuspend {
                if (this.exception is IOException) {
                    emit(DataState.Error<NewsArticlesResponse>(noNetworkErrorMessage()))
                } else {
                    emit(DataState.Error<NewsArticlesResponse>(message()))
                }
            }
        }
    }
}
