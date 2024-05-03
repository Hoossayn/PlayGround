package com.example.playground.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun PlayGroundNavHost(
    navController: NavHostController,
    onShowSnackBar: suspend (String, String?) -> Boolean,
) {
    val onNavigateUp: () -> Unit = {
        navController.navigateUp()
    }

}