package com.malik.newsreader.home

import com.malik.newsreader.data.NewsArticlesDataSource
import com.malik.newsreader.dataaccess.DataState
import com.malik.newsreader.dataaccess.remote.ApiInterface
import com.malik.newsreader.dataaccess.remote.ApiResponse
import com.malik.newsreader.dataaccess.remote.response_models.NewsArticlesResponse
import com.malik.newsreader.dataaccess.repository.NewsArticlesRepository
import com.malik.newsreader.dataaccess.repository.NewsArticlesRepositoryImp
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class NewsArticlesRepositoryTest {

    private lateinit var sut: NewsArticlesRepository
    private val apiService: ApiInterface = mockk()

    @Before
    fun setUp() {
        sut = NewsArticlesRepositoryImp(apiService)
    }

    @Test
    fun `getNewsArticles() should return success state with articles when API responds with success`() =
        runTest {
            // Given
            val sortBy = "relevancy"
            val givenArticles = NewsArticlesDataSource.newsArticlesResponse.articles
            val apiResponse = ApiResponse.create(
                response = Response.success(
                    NewsArticlesResponse(
                        articles = givenArticles,
                        status = "ok",
                        totalResults = 1
                    )
                )
            )

            coEvery { apiService.loadNewsArticles(any(), any(), any(), any()) } returns apiResponse

            // When
            val result =
                sut.getNewsArticles(page = 1, sortBy = sortBy).toList()

            // Then
            assert(result.first() is DataState.Success)

            val successState = result.first() as DataState.Success
            assertEquals(1, successState.data.size)
        }
}
