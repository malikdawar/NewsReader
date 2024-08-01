package com.malik.newsreader.ui.screens.home.presentation

sealed class HomeScreenSideEvent

data object GetArticles : HomeScreenSideEvent()
data object LoadMoreArticles : HomeScreenSideEvent()
