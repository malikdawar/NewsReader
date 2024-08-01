package com.malik.newsreader.dataaccess.usecases

import com.malik.newsreader.dataaccess.repository.NewsArticlesRepository
import javax.inject.Inject

/**
 * A [FetchNewsArticles].kt to check the status of consent
 * * @author Malik Dawar, malikdawar@hotmail.com
 */

class FetchNewsArticles @Inject constructor(
    private val repository: NewsArticlesRepository,
) {
    suspend operator fun invoke(pageNumber: Int) = repository.getAvailableNewsArticles(pageNumber)
}
