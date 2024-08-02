package com.malik.newsreader.dataaccess.models

data class NewsArticle(
    val title: String,
    val author: String,
    val description: String?,
    val content: String?,
    val publishedAt: String?,
    val source: String,
    val urlToImage: String?
)