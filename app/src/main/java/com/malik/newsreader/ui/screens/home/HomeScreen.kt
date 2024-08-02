package com.malik.newsreader.ui.screens.home

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.malik.newsreader.R
import com.malik.newsreader.common.Const.COMMAND_RELOAD
import com.malik.newsreader.common.PermissionManager.hasAudioPermission
import com.malik.newsreader.common.extensions.showToast
import com.malik.newsreader.dataaccess.models.NewsArticle
import com.malik.newsreader.ui.component.NavigationScreen
import com.malik.newsreader.ui.component.NewsArticlesList
import com.malik.newsreader.ui.component.ProgressDialog
import com.malik.newsreader.ui.component.SpeechRecognition
import com.malik.newsreader.ui.component.ToolBarDropDownMenu
import com.malik.newsreader.ui.screens.SharedViewModel
import com.malik.newsreader.ui.screens.home.presentation.ContentNextPageState
import com.malik.newsreader.ui.screens.home.presentation.ContentState
import com.malik.newsreader.ui.screens.home.presentation.ErrorState
import com.malik.newsreader.ui.screens.home.presentation.GetArticles
import com.malik.newsreader.ui.screens.home.presentation.HomeViewModel
import com.malik.newsreader.ui.screens.home.presentation.LoadingState
import kotlinx.coroutines.flow.collectLatest

/**
 * The [HomeScreen].kt the Home UI component
 * @author Malik Dawar, malikdawar@hotmail.com
 */
@Composable
fun HomeScreen(
    sharedVM: SharedViewModel,
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val isLoading = remember { mutableStateOf(true) }
    val articles = remember { mutableStateListOf<NewsArticle>() }
    val sortOptions = viewModel.sortOptions
    val selectedSortByOption by viewModel.sortBy.collectAsState(initial = null)
    val context = LocalContext.current

    val speechRecognitionManager = remember {
        SpeechRecognition(context) { text ->
            if (text.contains(COMMAND_RELOAD, ignoreCase = true)) {
                context.showToast("reloading")
                viewModel.onIntent(GetArticles(selectedSortByOption ?: sortOptions[0]))
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            speechRecognitionManager.startListening()
        } else {
            context.showToast("Audio permission is required for speech recognition.")
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onIntent(GetArticles(sortOptions[0]))
    }

    LaunchedEffect(viewModel.articlesList) {
        viewModel.articlesList.collectLatest {
            if (it.isNotEmpty()) {
                articles.clear()
                articles.addAll(it)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            ToolBarDropDownMenu(options = sortOptions) {
                viewModel.onIntent(GetArticles(it))
            }

            NewsArticlesList(articles = articles, viewModel = viewModel) {
                sharedVM.setArticle(it)
                navController.navigate(NavigationScreen.DetailsScreen.route)
            }
        }

        FloatingActionButton(
            onClick = {
                if (hasAudioPermission(context)) {
                    context.showToast("Listening......")
                    speechRecognitionManager.startListening()
                } else {
                    permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomStart)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_mic), contentDescription = "Speak")
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
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
