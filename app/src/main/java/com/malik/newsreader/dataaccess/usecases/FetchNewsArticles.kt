package com.malik.newsreader.dataaccess.usecases

import com.malik.newsreader.dataaccess.repository.NewsArticlesRepository
import javax.inject.Inject

/**
 * A [FetchNewsArticles].kt to fetch teh available news articles
 * * @author Malik Dawar, malikdawar@hotmail.com
 */

class FetchNewsArticles @Inject constructor(
    private val repository: NewsArticlesRepository,
) {
    suspend operator fun invoke(pageNumber: Int) = repository.getNewsArticles(pageNumber)
}
