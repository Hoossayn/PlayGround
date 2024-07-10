package com.example.ui.components

import android.os.Build
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.ui.component.TrailerButton
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
@Config(
    sdk = [Build.VERSION_CODES.O],
    instrumentedPackages = ["androidx.loader.content"],
    qualifiers = "xlarge",
)
class TrailerButtonTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    @Throws(Exception::class)
    fun setUp(){
        ShadowLog.stream = System.out
    }

    @Test
    fun trailerButtonTest_showTrailerButton() {
        with(composeTestRule) {
            setContent {
                TrailerButton()
            }

            onNodeWithText("Watch Trailer")
                .assertExists()
                .assertHasClickAction()
                .assertIsDisplayed()
                .performClick()
        }
    }
}
