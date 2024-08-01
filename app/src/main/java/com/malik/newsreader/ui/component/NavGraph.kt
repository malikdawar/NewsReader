package com.malik.newsreader.ui.component

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.malik.newsreader.ui.screens.SharedViewModel
import com.malik.newsreader.ui.screens.home.HomeScreen

@Composable
internal fun NavigationGraph(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
) {
    NavHost(navController = navController, startDestination = "homeFragment") {
        composable(
            route = "homeFragment",
        ) {
            HomeScreen(sharedViewModel)
        }
    }
}
