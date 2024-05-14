package com.example.playground.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.example.screens.movies.navigation.moviesRoutePattern
import com.example.screens.movies.navigation.moviesScreen
import com.example.screens.tvshows.navigation.tvShowsScreen

@Composable
fun PlayGroundNavHost(
    navController: NavHostController,
    onShowSnackBar: suspend (String, String?) -> Boolean,
) {
    val onNavigateUp: () -> Unit = {
        navController.navigateUp()
    }

    val onMovieClick: (Long) -> Unit = { movieId ->
        /*navController.navigateToMovieDetails(
            movieId = movieId,
            navOptions = navOptions {},
        )*/
    }


    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = moviesRoutePattern
    ) {
        moviesScreen(onMovieClick = onMovieClick)
        tvShowsScreen(onTVShowClick = {})

    }

}