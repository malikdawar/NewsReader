package com.malik.newsreader.home

import com.malik.newsreader.data.NewsArticlesDataSource
import com.malik.newsreader.dataaccess.DataState
import com.malik.newsreader.dataaccess.repository.NewsArticlesRepository
import com.malik.newsreader.dataaccess.usecases.FetchNewsArticlesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FetchNewsArticlesUseCaseTest {

    @MockK
    private lateinit var repository: NewsArticlesRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `given when the invoke in FetchNewsArticlesUseCase is called then it fetch the articles`() =
        runBlocking {
            // Given
            val sut = FetchNewsArticlesUseCase(repository)
            val givenArticles = NewsArticlesDataSource.newsArticles
            val sortBy = "relevant"

            // When
            coEvery { repository.getNewsArticles(1, sortBy) }
                .returns(flowOf(DataState.Success(givenArticles)))

            // Invoke
            val newsListFlow = sut(1, sortBy)

            // Then
            MatcherAssert.assertThat(newsListFlow, CoreMatchers.notNullValue())

            val newsListDataState = newsListFlow.first()
            MatcherAssert.assertThat(newsListDataState, CoreMatchers.notNullValue())
            MatcherAssert.assertThat(
                newsListDataState, CoreMatchers.instanceOf(DataState.Success::class.java)
            )

            val newsList = (newsListDataState as DataState.Success).data
            MatcherAssert.assertThat(newsList, CoreMatchers.notNullValue())
            MatcherAssert.assertThat(newsList.size, `is`(givenArticles.size))
            MatcherAssert.assertThat(newsList[0].title, `is`(givenArticles[0].title))
            MatcherAssert.assertThat(newsList[0].author, `is`(givenArticles[0].author))
            MatcherAssert.assertThat(newsList[0].description, `is`(givenArticles[0].description))
            MatcherAssert.assertThat(newsList[0].content, `is`(givenArticles[0].content))
            MatcherAssert.assertThat(newsList[0].publishedAt, `is`(givenArticles[0].publishedAt))
            MatcherAssert.assertThat(newsList[0].source, `is`(givenArticles[0].source))
            MatcherAssert.assertThat(newsList[0].urlToImage, `is`(givenArticles[0].urlToImage))
        }


    @Test
    fun `invoke() should return error state when repository fails`() = runBlocking {
        // Given
        val sut = FetchNewsArticlesUseCase(repository)
        val errorMessage = "Network Error"
        val sortBy = "relevancy"
        coEvery { repository.getNewsArticles(1, sortBy) } returns flowOf(
            DataState.Error(
                errorMessage
            )
        )
        // When
        val result = sut(1, sortBy).toList()
        // Then
        assert(result.first() is DataState.Error)
        assertEquals(errorMessage, (result.first() as DataState.Error).exception)
    }
}
