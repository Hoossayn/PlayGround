package com.example.playground.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(localStorage: LocalStorage): ViewModel() {

    val uiState: StateFlow<AppUIState> = localStorage.userData().map { userData ->
            AppUIState.Success(
                UserEditableSettings(

                )
            )
    }.stateIn (
        scope = viewModelScope,
        initialValue = AppUIState.Loading,
        started = SharingStarted.WhileSubscribed(5_000)
    )

}