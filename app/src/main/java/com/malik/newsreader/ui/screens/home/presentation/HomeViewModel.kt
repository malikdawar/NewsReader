package com.malik.newsreader.ui.screens.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malik.newsreader.dataaccess.DataState
import com.malik.newsreader.dataaccess.models.NewsArticle
import com.malik.newsreader.dataaccess.usecases.FetchNewsArticles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The [HomeViewModel].kt is viewmodel for HomeUi to do all the UI/logical operations
 * @author Malik Dawar, malikdawar@hotmail.com
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchArticlesUseCase: FetchNewsArticles
) : ViewModel() {
    private var pageNumber = 1
    private val _homeUiStateFlow = MutableStateFlow<HomeUiState>(LoadingState)
    val homeUiState: StateFlow<HomeUiState> get() = _homeUiStateFlow

    private var _articlesListFlow =
        MutableStateFlow<List<NewsArticle>>(emptyList())
    val articlesList: StateFlow<List<NewsArticle>> get() = _articlesListFlow

    val sortOptions = listOf("relevancy", "popularity", "publishedAt")
    private val _sortBy = MutableStateFlow(sortOptions[0])

    fun onIntent(intent: HomeScreenSideEvent) {
        when (intent) {
            is GetArticles -> {
                onSortOptionSelected(intent.sortBy)
                fetchArticles(page = pageNumber)
            }

            is LoadMoreArticles -> {
                loadMoreArticles()
            }
        }
    }

    private fun fetchArticles(page: Int) {
        viewModelScope.launch {
            _homeUiStateFlow.value = LoadingState
            fetchArticlesUseCase.invoke(pageNumber = page, sortBy = _sortBy.value)
                .collectLatest { dataState ->
                    when (dataState) {
                        is DataState.Success -> {
                            if (page == 1) {
                                // First page
                                _homeUiStateFlow.value = ContentState
                                _articlesListFlow.value = dataState.data
                            } else {
                                // Any other page
                                _homeUiStateFlow.value = ContentNextPageState

                                val currentList = arrayListOf<NewsArticle>()
                                _articlesListFlow.value.let { currentList.addAll(it) }
                                dataState.data.let {
                                    currentList.addAll(it)
                                }
                                _articlesListFlow.value = currentList
                            }
                        }

                        is DataState.Error -> {
                            _homeUiStateFlow.value = ErrorState(dataState.exception)
                        }
                    }
                }
        }
    }

    private fun onSortOptionSelected(sortBy: String) {
        _sortBy.value = sortBy
    }

    private fun loadMoreArticles() {
        pageNumber++
        fetchArticles(pageNumber)
    }
}
