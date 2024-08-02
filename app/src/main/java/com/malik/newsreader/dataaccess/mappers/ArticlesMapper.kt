package com.malik.newsreader.dataaccess.mappers

import com.malik.newsreader.dataaccess.models.NewsArticle
import com.malik.newsreader.dataaccess.remote.response_models.NewsArticlesResponse

/**
 * The [NewsArticleMapper].kt the Home UI component
 * @author Malik Dawar, malikdawar@hotmail.com
 */
fun List<NewsArticlesResponse.Article>.articleResponseToDomainModels(): List<NewsArticle> {
    return this.mapNotNull { it.toDomainModel() }
}

fun NewsArticlesResponse.Article.toDomainModel(): NewsArticle? {
    if (title == REMOVED || description == REMOVED) {
        return null
    }

    return NewsArticle(
        title = title,
        author = author ?: "Unknown Author",
        description = description,
        content = content,
        publishedAt = publishedAt,
        source = source?.name ?: "Unknown Source",
        urlToImage = urlToImage
    )
}

private const val REMOVED = "[Removed]"