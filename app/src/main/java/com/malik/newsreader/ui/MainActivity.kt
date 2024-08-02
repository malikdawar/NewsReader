package com.malik.newsreader.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.malik.newsreader.ui.component.NavigationGraph
import com.malik.newsreader.ui.component.theme.AppTheme
import com.malik.newsreader.ui.screens.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * The [MainActivity].kt the single activity of the project
 * @author Malik Dawar, malikdawar@hotmail.com
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            // Set up the navigation graph
            NavigationGraph(
                navController = navController,
                sharedViewModel = hiltViewModel() as SharedViewModel
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme {
        MainScreen()
    }
}
