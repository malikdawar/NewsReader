package com.malik.newsreader.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.malik.newsreader.dataaccess.models.NewsArticlesResponse
import com.malik.newsreader.ui.component.NavigationScreen
import com.malik.newsreader.ui.component.NewArticlesList
import com.malik.newsreader.ui.component.ProgressDialog
import com.malik.newsreader.ui.screens.SharedViewModel
import com.malik.newsreader.ui.screens.home.model.ContentNextPageState
import com.malik.newsreader.ui.screens.home.model.ContentState
import com.malik.newsreader.ui.screens.home.model.ErrorState
import com.malik.newsreader.ui.screens.home.model.GetArticles
import com.malik.newsreader.ui.screens.home.model.HomeViewModel
import com.malik.newsreader.ui.screens.home.model.LoadingState
import kotlinx.coroutines.flow.collectLatest

/**
 * The [HomeScreen].kt the Home UI component
 * @author Malik Dawar, malikdawar@hotmail.com
 */

@Composable
fun HomeScreen(
    sharedViewModel: SharedViewModel,
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val isLoading = remember { mutableStateOf(false) }
    val articles = remember { mutableStateListOf<NewsArticlesResponse.Article>() }

    LaunchedEffect(Unit) {
        viewModel.onIntent(GetArticles)
    }

    LaunchedEffect(viewModel.articlesList) {
        viewModel.articlesList.collectLatest {
            if (it.isNotEmpty()) {
                articles.clear()
                articles.addAll(it)
            }
        }
    }

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        NewArticlesList(articles = articles, viewModel = viewModel) {
            sharedViewModel.saveSelectedNewsArticle(it)
            navController.navigate(NavigationScreen.DetailsScreen.route)
        }

        ProgressDialog(isLoading.value)
    }

    viewModel.homeUiState.collectAsState().value.let {
        when (it) {
            is LoadingState -> {
                isLoading.value = true
            }

            is ContentState, is ContentNextPageState -> {
                isLoading.value = false
            }

            is ErrorState -> {
                isLoading.value = false
            }
        }
    }
}
