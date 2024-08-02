package com.malik.newsreader.dataaccess.usecases

import com.malik.newsreader.dataaccess.repository.NewsArticlesRepository
import javax.inject.Inject

/**
 * A [FetchNewsArticlesUseCase].kt to fetch teh available news articles
 * * @author Malik Dawar, malikdawar@hotmail.com
 */
class FetchNewsArticlesUseCase @Inject constructor(
    private val repository: NewsArticlesRepository,
) {
    suspend operator fun invoke(pageNumber: Int, sortBy: String) =
        repository.getNewsArticles(pageNumber, sortBy)
}
