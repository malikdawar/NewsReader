package com.malik.newsreader.ui.screens

import androidx.lifecycle.ViewModel
import com.malik.newsreader.dataaccess.models.NewsArticlesResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel : ViewModel() {

    private val _selectedNewsArticle = MutableStateFlow<NewsArticlesResponse.Article?>(null)
    val selectedNewsArticle: StateFlow<NewsArticlesResponse.Article?> = _selectedNewsArticle

    fun saveSelectedNewsArticle(article: NewsArticlesResponse.Article) {
        _selectedNewsArticle.value = article
    }
}
