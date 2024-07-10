package com.example.ui.widgets

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.dp
import coil.Coil
import com.example.core.test.util.captureMultiTheme
import com.example.core.test.util.fakeSuccessImageLoader
import com.example.core.ui.component.MoviePoster
import com.example.designSystem.core.theme.PlayGround
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
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
class MoviePosterScreenshotTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        Coil.setImageLoader(fakeSuccessImageLoader)
        ShadowLog.stream = System.out
    }

    @Test
    fun moviePoster_multipleThemes() {
        composeTestRule.captureMultiTheme("MoviePoster") { desc: String ->
            Column(
                modifier = Modifier
                    .padding(PlayGround.spacing.twoAndaHalf)
                    .background(PlayGround.color.inverseOnSurface),
            ) {
                MoviePoster(
                    modifier = Modifier
                        .width(100.dp)
                        .aspectRatio(0.8F),
                    posterUrl = "https://example.com/image.jpg",
                    contentDescription = "Image",
                    shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.Window),
                ) {}
            }
        }
    }
}
