package com.malik.newsreader.dataaccess.models

/**
 * The [NewsArticle].kt the Home UI component
 * @author Malik Dawar, malikdawar@hotmail.com
 */
data class NewsArticle(
    val title: String,
    val author: String,
    val description: String?,
    val content: String?,
    val publishedAt: String?,
    val source: String,
    val urlToImage: String?
)