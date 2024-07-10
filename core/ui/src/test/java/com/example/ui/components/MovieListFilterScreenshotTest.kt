package com.example.ui.components

import androidx.activity.ComponentActivity
import androidx.compose.material3.Surface
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.core.model.MovieListFilterItem
import com.example.core.test.util.captureMultiTheme
import com.example.core.ui.component.MovieListFilter
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
class MovieListFilterScreenshotTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun movieListFilter_multipleThemes() {
        composeTestRule.captureMultiTheme("MovieListFilter") { desc: String ->
            Surface {
                MovieListFilter(
                    items = listOf(
                        MovieListFilterItem(
                            isSelected = true,
                            type = MovieListFilterItem.FilterType.NOW_PLAYING,
                        ),
                        MovieListFilterItem(
                            isSelected = false,
                            type = MovieListFilterItem.FilterType.POPULAR,
                        ),
                        MovieListFilterItem(
                            isSelected = false,
                            type = MovieListFilterItem.FilterType.TOP_RATED,
                        ),
                        MovieListFilterItem(
                            isSelected = false,
                            type = MovieListFilterItem.FilterType.UPCOMING,
                        ),
                    ).map { Pair(labelFor(it), it.isSelected) },
                ) {}
            }
        }
    }

    private fun labelFor(filterItem: MovieListFilterItem): String {
        return when (filterItem.type) {
            MovieListFilterItem.FilterType.NOW_PLAYING -> "Now Playing"
            MovieListFilterItem.FilterType.POPULAR -> "Popular"
            MovieListFilterItem.FilterType.TOP_RATED -> "Top Rated"
            MovieListFilterItem.FilterType.UPCOMING -> "Upcoming"
            MovieListFilterItem.FilterType.DISCOVER -> "Discover"
        }
    }
}
