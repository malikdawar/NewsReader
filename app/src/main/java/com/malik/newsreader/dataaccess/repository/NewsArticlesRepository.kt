package com.malik.newsreader.dataaccess.repository

import com.malik.newsreader.dataaccess.DataState
import com.malik.newsreader.dataaccess.models.NewsArticlesResponse
import kotlinx.coroutines.flow.Flow

/**
 * The [NewsArticlesRepository], interface of repo-data-layer to handle communication with any data source
 * see implementation here [NewsArticlesRepositoryImp]
 * @author Malik Dawar, malikdawar@hotmail.com
 */
interface NewsArticlesRepository {
    suspend fun getAvailableNewsArticles(page: Int): Flow<DataState<NewsArticlesResponse>>

}
