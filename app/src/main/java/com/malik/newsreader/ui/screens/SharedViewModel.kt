package com.malik.newsreader.ui.screens

import androidx.lifecycle.ViewModel
import com.malik.newsreader.dataaccess.models.NewsArticle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel : ViewModel() {

    private val _selectedNewsArticle = MutableStateFlow<NewsArticle?>(null)
    val selectedNewsArticle: StateFlow<NewsArticle?> = _selectedNewsArticle

    fun setSelectedNewsArticle(article: NewsArticle) {
        _selectedNewsArticle.value = article
    }
}
