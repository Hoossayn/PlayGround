package com.example.core.model

data class UserData(
    val accountId: String?,
    val isLoggedIn: Boolean,
    val themeConfig: ThemeConfig,
    val usDynamicColor: Boolean,
    val name: String?,
    val userName: String?,
)
