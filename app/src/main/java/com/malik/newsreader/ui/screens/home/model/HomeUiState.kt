package com.malik.newsreader.ui.screens.home.model

internal sealed class HomeUiState

internal data object LoadingState : HomeUiState()
internal data object ContentState : HomeUiState()
internal data object ContentNextPageState : HomeUiState()
internal class ErrorState(val message: String) : HomeUiState()
