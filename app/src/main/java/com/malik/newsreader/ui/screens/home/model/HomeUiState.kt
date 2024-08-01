package com.malik.newsreader.ui.screens.home.model

sealed class HomeUiState

data object LoadingState : HomeUiState()
data object ContentState : HomeUiState()
data object ContentNextPageState : HomeUiState()
class ErrorState(val message: String) : HomeUiState()
