package com.example.playground.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.repository.LocalStorage
import com.example.core.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import com.example.core.model.UserEditableSettings
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor (localStorage: LocalStorage): ViewModel() {
    @Suppress("MagicNumber")
    val uiState: StateFlow<AppUIState> = localStorage.userData().map { userData ->
            AppUIState.Success(
                UserEditableSettings(
                    isLoggedIn = userData.isLoggedIn,
                    themeConfig = userData.themeConfig,
                    isDynamicColor = userData.usDynamicColor,
                )
            )
    }.stateIn (
        scope = viewModelScope,
        initialValue = AppUIState.Loading,
        started = SharingStarted.WhileSubscribed(5_000)
    )

}