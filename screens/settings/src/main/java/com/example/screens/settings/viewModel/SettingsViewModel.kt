package com.example.screens.settings.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.repository.LocalStorage
import com.example.core.model.ThemeConfig
import com.example.core.model.UserEditableSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
    @Inject
    constructor(private val localStorage: LocalStorage) : ViewModel() {
        val settingsUiState: StateFlow<SettingsUiState> = localStorage.userData()
            .map {
                SettingsUiState.Success(
                    preference = UserEditableSettings(
                        isLoggedIn = it.isLoggedIn,
                        themeConfig = it.themeConfig,
                        isDynamicColor = it.usDynamicColor,
                    ),
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = SettingsUiState.Loading,
            )

        fun onLogout() {
            viewModelScope.launch { localStorage.logout() }
        }

        fun onThemeConfig(config: ThemeConfig) {
            viewModelScope.launch { localStorage.setThemeConfig(config) }
        }

        fun onDynamicColourPreferenceChanged(useDynamicColor: Boolean) {
            viewModelScope.launch { localStorage.setUseDynamicColor(useDynamicColor) }
        }
    }

sealed interface SettingsUiState {
    data object Loading : SettingsUiState

    data class Success(val preference: UserEditableSettings) : SettingsUiState
}
