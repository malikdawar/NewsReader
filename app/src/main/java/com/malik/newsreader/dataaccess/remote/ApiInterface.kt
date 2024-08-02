package com.malik.newsreader.dataaccess.remote

import com.malik.newsreader.dataaccess.remote.response_models.NewsArticlesResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The [ApiInterface].kt the Home UI component
 * @author Malik Dawar, malikdawar@hotmail.com
 */
interface ApiInterface {
    @GET(SERVICE_API)
    suspend fun loadNewsArticles(
        @Query("q") query: String = "tech",
        @Query("sortBy") sortBy: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int
    ): ApiResponse<NewsArticlesResponse>

    companion object {
        const val SERVICE_API = "everything"
    }
}