package com.malik.newsreader.data

import com.malik.newsreader.dataaccess.models.NewsArticle
import com.malik.newsreader.dataaccess.remote.response_models.NewsArticlesResponse


object NewsArticlesDataSource {

    val newsArticlesResponse = NewsArticlesResponse(
        articles = listOf(
            NewsArticlesResponse.Article(
                author = "news_author",
                content = "news_content",
                description = "news_description",
                publishedAt = "2024-08-02T10:00:00Z",
                source = NewsArticlesResponse.Article.Source(
                    id = "source_id",
                    name = "source_name"
                ),
                title = "news_title",
                url = "news_url",
                urlToImage = "news_image"
            )
        ),
        status = "ok",
        totalResults = 2
    )


    val newsArticles: List<NewsArticle>
        get() = listOf(
            NewsArticle(
                title = "news_title",
                author = "news_author",
                description = "news_ description",
                content = "news_ content",
                publishedAt = "news_ publishedAt",
                source = "news_ source",
                urlToImage = "news_ urlToImage",
            )
        )
}