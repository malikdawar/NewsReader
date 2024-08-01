package com.malik.newsreader.ui.screens.home.model

internal sealed class HomeScreenSideEvent

internal data object GetArticles : HomeScreenSideEvent()
internal data object LoadMoreArticles : HomeScreenSideEvent()
