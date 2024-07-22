package com.example.ui.widgets

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.dp
import com.example.core.test.util.captureMultiTheme
import com.example.core.ui.widget.MovieRating
import com.example.designSystem.core.theme.PlayGround
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(application = HiltTestApplication::class, sdk = [33], qualifiers = "480dpi")
@LooperMode(LooperMode.Mode.PAUSED)
class MovieRatingScreenshotTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun movieRating_multipleThemes() {
        composeTestRule.captureMultiTheme("MovieRating") {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .aspectRatio(1F)
                    .background(PlayGround.color.inverseOnSurface),
            ) {
                MovieRating(
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center),
                    rating = 0.45F,
                )
            }
        }
    }
}
