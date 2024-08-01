package com.malik.newsreader.dataaccess.remote

import com.malik.newsreader.dataaccess.remote.models.NewsArticlesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET(SERVICE_API)
    suspend fun loadNewsArticles(
        @Query("q") query: String = "tech",
        @Query("sortBy") sortBy: String, // relevancy, popularity, publishedAt.
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int
    ): ApiResponse<NewsArticlesResponse>

    companion object {
        const val SERVICE_API = "everything"
    }
}