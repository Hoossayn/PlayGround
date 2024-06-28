package com.example.screens.people.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.core.ui.navigation.enterTransition
import com.example.core.ui.navigation.exitTransition
import com.example.core.ui.navigation.popEnterTransition
import com.example.core.ui.navigation.popExitTransition
import com.example.screens.people.ui.PeopleScreen

@Suppress("TopLevelPropertyNaming", "ktlint:standard:property-naming")
const val peopleNavPattern = "/people"

fun NavGraphBuilder.peopleScreen(onPersonClick: (Long) -> Unit) {
    composable(
        route = peopleNavPattern,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
    ) {
        PeopleScreen(onPersonClick = onPersonClick)
    }
}
