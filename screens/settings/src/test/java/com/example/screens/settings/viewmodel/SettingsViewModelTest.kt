package com.example.screens.settings.viewmodel

import app.cash.turbine.test
import com.example.core.data.repository.LocalStorage
import com.example.core.model.ThemeConfig
import com.example.core.model.UserData
import com.example.core.model.UserEditableSettings
import com.example.core.test.util.MainDispatcherRule
import com.example.screens.settings.viewModel.SettingsUiState
import com.example.screens.settings.viewModel.SettingsViewModel
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var settingsViewModel: SettingsViewModel

    private val testUserData = UserData(
        accountId = "4bc889XXXXa3c0z92001001",
        isLoggedIn = false,
        themeConfig = ThemeConfig.DARK,
        usDynamicColor = false,
        name = "Hakeem",
        userName = "Hoossayn",
    )

    private val localStorage = mockk<LocalStorage>(relaxed = true) {
        every { userData() } returns flowOf(testUserData)
    }

    @Before
    fun setUp() {
        settingsViewModel = SettingsViewModel(localStorage = localStorage)
    }

    @Test
    fun settingsViewModel_onLoad_returnUserData() = runTest {
        settingsViewModel.settingsUiState.test {
            Truth.assertThat((awaitItem() as SettingsUiState.Success).preference).isEqualTo(
                UserEditableSettings(
                    isLoggedIn = false,
                    themeConfig = ThemeConfig.DARK,
                    isDynamicColor = false,
                ),
            )
        }
        verify(exactly = 1) { localStorage.userData() }
    }

    @Test
    fun settingsViewModel_onThemeConfig_themeConfigIsShow() = runTest {
        coEvery { localStorage.setThemeConfig(any()) } returns Unit
        settingsViewModel.onThemeConfig(ThemeConfig.DARK)
        advanceUntilIdle()

        verify(exactly = 1) { localStorage.userData() }
        coVerify(exactly = 1) { localStorage.setThemeConfig(eq(ThemeConfig.DARK)) }
    }

    @Test
    fun settingsViewModel_onChangeDynamicColorPreference_correctColorPrefIsSet() = runTest {
        coEvery { localStorage.setUseDynamicColor(any()) } returns Unit
        settingsViewModel.onDynamicColourPreferenceChanged(true)
        advanceUntilIdle()

        verify(exactly = 1) { localStorage.userData() }
        coVerify(exactly = 1) { localStorage.setUseDynamicColor(eq(true)) }
    }
}
