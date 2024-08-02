package com.malik.newsreader.ui.component

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.malik.newsreader.ui.screens.SharedViewModel
import com.malik.newsreader.ui.screens.details.NewsDetailScreen
import com.malik.newsreader.ui.screens.home.HomeScreen

/**
 * The [NavigationGraph].kt the Home UI component
 * @author Malik Dawar, malikdawar@hotmail.com
 */
@Composable
fun NavigationGraph(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
) {
    NavHost(navController = navController, startDestination = NavigationScreen.HomeScreen.route) {
        composable(
            route = NavigationScreen.HomeScreen.route,
        ) {
            HomeScreen(sharedViewModel, navController)
        }

        composable(
            route = NavigationScreen.DetailsScreen.route,
        ) {
            NewsDetailScreen(sharedViewModel)
        }
    }
}

enum class NavigationScreen(val route: String) {
    HomeScreen("homeScreen"),
    DetailsScreen("detailsScreen")
}
