package com.example.designSystem.component

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.core.test.util.captureMultiTheme
import com.example.designSystem.core.component.PlayGroundTextButton
import com.example.designSystem.core.theme.PlayGround
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.robolectric.annotation.LooperMode
import org.robolectric.shadows.ShadowLog

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(application = HiltTestApplication::class, sdk = [33], qualifiers = "480dpi")
@LooperMode(LooperMode.Mode.PAUSED)
class PlayButtonTextButtonTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        ShadowLog.stream = System.out
    }

    @Test
    fun playGroundButtonTextButton_multipleThemes() {
        composeTestRule.captureMultiTheme("PlayGroundTextButton") { desc: String ->
            Column(
                modifier = Modifier
                    .padding(PlayGround.spacing.twoAndaHalf)
                    .background(PlayGround.color.inverseOnSurface),
            ) {
                PlayGroundTextButton(onClick = {}, content = { Text("Button") })
            }
        }
    }
}
