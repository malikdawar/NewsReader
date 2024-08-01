package com.malik.newsreader.ui.screens.home.model

sealed class HomeScreenSideEvent

data object GetArticles : HomeScreenSideEvent()
data object LoadMoreArticles : HomeScreenSideEvent()
