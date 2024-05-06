package com.example.playground.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import com.example.core.model.UserEditableSettings
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject


class AppViewModel(localStorage: Flow<UserData>): ViewModel() {

    val uiState: StateFlow<AppUIState> = localStorage.map { userData ->
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