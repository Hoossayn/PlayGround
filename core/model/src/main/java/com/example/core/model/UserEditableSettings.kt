package com.example.core.model

data class UserEditableSettings(
    val isLoggedIn: Boolean = false,
    val themeConfig: ThemeConfig = ThemeConfig.DARK,
    val isDynamicColor: Boolean = false,
)
