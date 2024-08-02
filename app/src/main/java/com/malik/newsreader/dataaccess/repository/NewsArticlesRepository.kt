package com.malik.newsreader.dataaccess.repository

import com.malik.newsreader.dataaccess.DataState
import com.malik.newsreader.dataaccess.models.NewsArticle
import kotlinx.coroutines.flow.Flow

/**
 * The [NewsArticlesRepository], interface of repo-data-layer to handle communication with any data source
 * see implementation here [NewsArticlesRepositoryImp]
 * @author Malik Dawar, malikdawar@hotmail.com
 */
interface NewsArticlesRepository {
    suspend fun getNewsArticles(page: Int, sortBy: String): Flow<DataState<List<NewsArticle>>>
}
