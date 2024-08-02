package com.malik.newsreader.ui.screens.home.presentation

sealed class HomeScreenSideEvent

data class GetArticles(val sortBy: String) : HomeScreenSideEvent()
data object LoadMoreArticles : HomeScreenSideEvent()
