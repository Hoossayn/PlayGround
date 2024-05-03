package com.example.playground.viewmodel

sealed interface AppUIState {

    data object Loading: AppUIState

    data class Success(val settings: UserEditableSettings) : AppUIState
}