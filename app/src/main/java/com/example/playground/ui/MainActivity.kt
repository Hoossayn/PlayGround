package com.example.playground.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.core.common.injection.IoDispatcher
import com.example.core.model.ThemeConfig
import com.example.designSystem.core.theme.PlayGroundTheme
import com.example.playground.viewmodel.AppUIState
import com.example.playground.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

   // @Inject
    //lateinit var lazyStats: dagger.Lazy<JankStats>

    @IoDispatcher
    @Inject
    lateinit var ioDispatcher: CoroutineDispatcher

    private val appViewModel by viewModels<AppViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        var uiState: AppUIState by mutableStateOf(AppUIState.Loading)

        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                appViewModel.uiState.onEach { uiState = it }.collect()
            }
        }

        splashScreen.setKeepOnScreenCondition{
            when (uiState) {
                AppUIState.Loading -> true
                is AppUIState.Success -> false
            }
        }

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val isDarkTheme = shouldUseDarkTheme(uiState = uiState)
            val isDynamicColor = shouldUseDynamicColor(uiState = uiState)

            PlayGroundTheme (
                isDarkTheme = isDarkTheme,
                isDynamicColor = isDynamicColor,
            ) {
                PlayGroundApp()
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }
}


@Composable
private fun shouldUseDarkTheme(uiState: AppUIState): Boolean = when (uiState) {
    AppUIState.Loading -> isSystemInDarkTheme()
    is  AppUIState.Success -> when (uiState.settings.themeConfig) {
        ThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
        ThemeConfig.LIGHT -> false
        ThemeConfig.DARK -> true
    }
}

@Composable
private fun shouldUseDynamicColor(uiState: AppUIState): Boolean = when (uiState) {
    AppUIState.Loading -> false
    is AppUIState.Success -> uiState.settings.isDynamicColor
}
