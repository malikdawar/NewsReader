package com.malik.newsreader.ui.screens

import androidx.lifecycle.ViewModel
import com.malik.newsreader.dataaccess.models.NewsArticle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * The [SharedViewModel].kt the Home UI component
 * @author Malik Dawar, malikdawar@hotmail.com
 */
class SharedViewModel : ViewModel() {

    private val _selectedArticle = MutableStateFlow<NewsArticle?>(null)
    val selectedArticle: StateFlow<NewsArticle?> = _selectedArticle

    fun setArticle(article: NewsArticle) {
        _selectedArticle.value = article
    }
}
