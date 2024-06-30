package com.example.screens.settings.ui

import android.os.Build
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.isSelectable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import coil.Coil
import com.example.core.model.ThemeConfig
import com.example.core.model.UserEditableSettings
import com.example.core.test.rule.assertAreDisplayed
import com.example.core.test.util.fakeSuccessImageLoader
import com.example.screens.settings.viewModel.SettingsUiState
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog

@RunWith(AndroidJUnit4::class)
@Config(
    sdk = [Build.VERSION_CODES.S],
    instrumentedPackages = ["androidx.loader.content"],
    qualifiers = "xlarge",
)
class SettingsScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private var onLoginClick = 0
    private var onChangeDynamicColorPreference = 0
    private var onChangeDarkThemeConfig = 0

    @Before
    @Throws(Exception::class)
    fun setUp() {
        Coil.setImageLoader(fakeSuccessImageLoader)
        ShadowLog.stream = System.out
    }

    @Test
    fun settingsScreen_settingsUiStateLoading_progressIndicatorIsShown() {
        composeTestRule.apply {
            setContent {
                SettingsScreen(
                    settingsUiState = SettingsUiState.Loading,
                    onChangeDynamicColorPreference = { onChangeDynamicColorPreference++ },
                    onChangeDarkThemeConfig = { onChangeDarkThemeConfig++ },
                    onLoginClick = { onLoginClick++ },
                    onLogoutClick = {}
                )
            }
            onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate))
                .assertIsDisplayed()
        }
    }

    @Test
    fun settingsScreen_onLoad_settingsScreenIsShownWithLoginButton() {
        composeTestRule.apply {
            setContent {
                SettingsScreen(
                    settingsUiState = SettingsUiState.Success(
                        preference = UserEditableSettings(
                            isLoggedIn = false,
                            themeConfig = ThemeConfig.DARK,
                            isDynamicColor = false
                        )
                    ),
                    onChangeDynamicColorPreference = { onChangeDynamicColorPreference++ },
                    onChangeDarkThemeConfig = { onChangeDarkThemeConfig++ },
                    onLoginClick = { onLoginClick++},
                    onLogoutClick = { }
                )
            }

            onNodeWithText("Hakeem").assertIsDisplayed()
            onNodeWithText("Member since November 2016").assertIsDisplayed()
            onNodeWithText("A software engineer! Member since November 2016").assertIsDisplayed()
            onNodeWithText("Stats").assertIsDisplayed()
            onAllNodesWithText("Settings").assertCountEquals(2).assertAreDisplayed()
            onNodeWithText("Login").assertIsDisplayed().performClick()
        }

        assertThat(onLoginClick).isEqualTo(1)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun settingsScreen_onChangeThemeButtonClick_showChangeThemeDialog() {
        composeTestRule.apply {
            setContent {
                SettingsScreen(
                    settingsUiState = SettingsUiState.Success(
                        preference = UserEditableSettings(
                            isLoggedIn = false,
                            themeConfig = ThemeConfig.DARK,
                            isDynamicColor = true
                        )
                    ),
                    supportDynamicColor = true,
                    onChangeDynamicColorPreference = { onChangeDynamicColorPreference++ },
                    onChangeDarkThemeConfig = {onChangeDarkThemeConfig++},
                    onLoginClick = { onLoginClick++ },
                    onLogoutClick = {}
                )
            }

            onNodeWithContentDescription("Change theme", true).assertIsDisplayed()
                .performClick()
            waitForIdle()
            onNode(isDialog()).assertExists()
            waitUntilNodeCount(hasText("Use Dynamic Color"), 1)
            onNodeWithText("Use Dynamic Color", useUnmergedTree = true).assertIsDisplayed()
            onNode(hasText("Yes") and isSelectable()).assertIsDisplayed().assertIsSelected()
            onNode(hasText("No") and isSelectable()).assertIsDisplayed().assertIsNotSelected()
                .performClick()
            onNodeWithText("Dark Mode Preference", useUnmergedTree = true).assertIsDisplayed()
            onNode(hasText("Dark") and isSelectable()).assertIsDisplayed().assertIsSelected()
            onNode(hasText("Light") and isSelectable()).assertIsDisplayed().assertIsNotSelected()
            onNode(hasText("System default") and isSelectable()).assertIsDisplayed()
                .assertIsNotSelected().performClick()
            onNodeWithText("OK").assertIsDisplayed().performClick()
            onNode(isDialog()).assertDoesNotExist()
        }

        assertThat(onChangeDynamicColorPreference).isEqualTo(1)
        assertThat(onChangeDarkThemeConfig).isEqualTo(1)
    }
}
