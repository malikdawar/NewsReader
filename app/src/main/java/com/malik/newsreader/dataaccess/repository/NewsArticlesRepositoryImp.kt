package com.malik.newsreader.dataaccess.repository

import androidx.annotation.WorkerThread
import com.malik.newsreader.common.extensions.noNetworkErrorMessage
import com.malik.newsreader.common.extensions.nothingFound
import com.malik.newsreader.dataaccess.DataState
import com.malik.newsreader.dataaccess.mappers.articleResponseToDomainModels
import com.malik.newsreader.dataaccess.models.NewsArticle
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
    override suspend fun getNewsArticles(page: Int): Flow<DataState<List<NewsArticle>>> {
        return flow {
            apiService.loadNewsArticles(
                query = "tech",
                sortBy = "popularity",
                pageSize = 50,
                page = page
            ).apply {
                onSuccessSuspend {
                    // handle the case when the API request gets an error response.
                    data?.articles?.let {
                        emit(DataState.Success(it.articleResponseToDomainModels()))
                    } ?: emit(DataState.Error<List<NewsArticle>>(nothingFound()))
                }
            }.onErrorSuspend {
                emit(DataState.Error<List<NewsArticle>>(message()))
            }.onExceptionSuspend {
                // handle the case when the API request gets an exception response.
                if (this.exception is IOException) {
                    emit(DataState.Error<List<NewsArticle>>(noNetworkErrorMessage()))
                } else {
                    emit(DataState.Error<List<NewsArticle>>(message()))
                }
            }
        }
    }
}
