package com.malik.newsreader.dataaccess.remote

import com.malik.newsreader.dataaccess.models.NewsArticlesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    //q=tech&sortBy=popularity&pageSize=10&page=10

    @GET(SERVICE_API)
    suspend fun loadNewsArticles(
        @Query("q") query: String = "tech",
        @Query("sortBy") sortBy: String, // relevancy, popularity, publishedAt.
       // @Query("apiKey") apiKey: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int
    ): ApiResponse<NewsArticlesResponse>

    companion object {
        const val SERVICE_API = "everything"
    }
}