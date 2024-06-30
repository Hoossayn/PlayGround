package com.example.screens.moviedetails.navigation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.screens.moviedetails.ui.MoviesDetailsScreen
import kotlinx.coroutines.flow.StateFlow

@VisibleForTesting
@Suppress("TopLevelPropertyNaming", "ktlint:standard:property-naming")
internal const val movieIdArg = "movieId"

@Suppress("TopLevelPropertyNaming", "ktlint:standard:property-naming")
const val movieDetailsRoutePattern = "movie/{$movieIdArg}"

internal class MovieDetailsArg(val movieId: StateFlow<Long>) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        movieId = checkNotNull(savedStateHandle.getStateFlow(movieIdArg, 0L)),
    )
}

fun NavController.navigateToMovieDetails(
    movieId: Long,
    navOptions: NavOptions? = null,
) {
    this.navigate(route = "movie/$movieId", navOptions = navOptions)
}

fun NavGraphBuilder.movieDetailsScreen(
    onMovieItemClick: (Long) -> Unit,
    onWatchTrailerClick: () -> Unit = { },
    onNavigateUp: () -> Unit,
) {
    composable(
        route = movieDetailsRoutePattern,
        arguments = listOf(
            navArgument(movieIdArg) {
                type = NavType.LongType
                defaultValue = 0L
            },
        )
    ) {
        MoviesDetailsScreen(
            onMovieItemClick = onMovieItemClick,
            onWatchTrailerClick = onWatchTrailerClick,
            onBackPress = onNavigateUp,
        )
    }
}
