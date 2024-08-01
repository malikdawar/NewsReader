package com.malik.newsreader.ui.screens.home.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malik.newsreader.dataaccess.DataState
import com.malik.newsreader.dataaccess.models.NewsArticlesResponse
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
        MutableStateFlow<List<NewsArticlesResponse.Article>>(emptyList())
    val articlesList: StateFlow<List<NewsArticlesResponse.Article>> get() = _articlesListFlow

    fun onIntent(intent: HomeScreenSideEvent) {
        when (intent) {
            is GetArticles -> {
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
            fetchArticlesUseCase.invoke(pageNumber = page).collectLatest { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        if (page == 1) {
                            // First page
                            _homeUiStateFlow.value = ContentState
                            _articlesListFlow.value = dataState.data.articles!!
                        } else {
                            // Any other page
                            _homeUiStateFlow.value = ContentNextPageState

                            val currentList = arrayListOf<NewsArticlesResponse.Article>()
                            _articlesListFlow.value.let { currentList.addAll(it) }
                            dataState.data.let {
                                currentList.addAll(it.articles!!)
                            }
                            _articlesListFlow.value = currentList
                        }
                    }

                    is DataState.Error -> {
                        _homeUiStateFlow.value = ErrorState("dataState.message")
                    }
                }
            }
        }
    }

    private fun loadMoreArticles() {
        pageNumber++
        fetchArticles(pageNumber)
    }
}
