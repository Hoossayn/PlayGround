package com.example.ui.components

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.dp
import coil.Coil
import com.example.core.test.util.captureMultiTheme
import com.example.core.test.util.fakeSuccessImageLoader
import com.example.core.ui.component.PersonItemView
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
class PersonItemScreenshotTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        Coil.setImageLoader(fakeSuccessImageLoader)
        ShadowLog.stream = System.out
    }

    @Test
    fun personItemView_multipleThemes() {
        composeTestRule.captureMultiTheme("PersonItemView") { desc: String ->
            Surface {
                PersonItemView(
                    modifier = Modifier
                        .width(250.dp)
                        .padding(PlayGround.spacing.twoAndaHalf),
                    name = "Sandra Bullock",
                    knownFor = "Rush Hour, Rush Hour 2, and Rush Hour 3",
                    imageUrl = "https://www.example.com/image.jpg",
                )
            }
        }
    }
}
