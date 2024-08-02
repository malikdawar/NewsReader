package com.malik.newsreader.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.malik.newsreader.data.NewsArticlesDataSource
import com.malik.newsreader.dataaccess.DataState
import com.malik.newsreader.dataaccess.usecases.FetchNewsArticlesUseCase
import com.malik.newsreader.ui.screens.home.presentation.ContentState
import com.malik.newsreader.ui.screens.home.presentation.ErrorState
import com.malik.newsreader.ui.screens.home.presentation.GetArticles
import com.malik.newsreader.ui.screens.home.presentation.HomeViewModel
import com.malik.newsreader.ui.screens.home.presentation.LoadMoreArticles
import com.malik.newsreader.utils.TestCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HomeViewModelTest {

    // Subject under test
    private lateinit var sut: HomeViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = TestCoroutineRule()

    @MockK
    lateinit var fetchNewsArticlesUseCase: FetchNewsArticlesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = HomeViewModel(fetchNewsArticlesUseCase)
    }

    @Test
    fun `onIntent() is called with GetArticles, should fetch articles list`() {
        // Given
        val articles = NewsArticlesDataSource.newsArticles
        val sortBy = "relevant"
        // When
        coEvery { fetchNewsArticlesUseCase.invoke(1, sortBy) }
            .returns(flowOf(DataState.Success(articles)))

        // Invoke
        sut.onIntent(GetArticles(sortBy))

        // Then
        coVerify(exactly = 1) { fetchNewsArticlesUseCase.invoke(1, sortBy) }
        assertEquals(ContentState, sut.homeUiState.value)
        assertEquals(articles.size, sut.articlesList.value.size)
    }

    @Test
    fun `onIntent() is called with LoadMoreArticles, fetch next page and update articles list`() = runTest {
        // Given
        val initialArticles = NewsArticlesDataSource.newsArticles
        val moreArticles = listOf(initialArticles[0], initialArticles[0])
        val sortBy = "relevant"

        coEvery { fetchNewsArticlesUseCase.invoke(1, sortBy) } returns flowOf(
            DataState.Success(
                initialArticles
            )
        )

        coEvery { fetchNewsArticlesUseCase.invoke(2, sortBy) } returns flowOf(
            DataState.Success(
                moreArticles
            )
        )

        // Initial Fetch
        sut.onIntent(GetArticles(sortBy = sortBy))
        // When
        sut.onIntent(LoadMoreArticles)

        // Then
        val expectedArticles = initialArticles + moreArticles
        assertEquals(expectedArticles, sut.articlesList.value)
    }

    @Test
    fun `fetchArticles() with page 1 should update state to ErrorState on failure`() = runTest {
        // Given
        val errorMessage = "Network Error"
        val sortBy = "relevant"

        coEvery { fetchNewsArticlesUseCase.invoke(1, sortBy) } returns flowOf(
            DataState.Error(errorMessage)
        )
        // When
        sut.onIntent(GetArticles(sortBy = sortBy))

        // Then
        val currentState = sut.homeUiState.value
        assert(currentState is ErrorState && currentState.message == errorMessage)
    }
}