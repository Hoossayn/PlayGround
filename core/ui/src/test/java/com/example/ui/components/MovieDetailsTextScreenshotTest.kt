package com.example.ui.components

import androidx.activity.ComponentActivity
import androidx.compose.material3.LocalTextStyle
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.example.core.test.util.captureMultiTheme
import com.example.core.ui.R
import com.example.core.ui.component.MovieDetailsText
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
class MovieDetailsTextScreenshotTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun movieDetailsText_multipleThemes() {
        composeTestRule.captureMultiTheme("MovieDetailsText") { desc: String ->
            val text =
                "Terrible Script, dialogue, directing, hammy editing. Music is meh. Poor acting" +
                    " (but they clearly got no support). This crap show's the contempt " +
                    "producers like this have for the audience that gives them money. " +
                    "The writer, director, producers and editor must have been so full of"
            MovieDetailsText(
                text = text,
                textAlign = TextAlign.Justify,
                modifier = Modifier,
                dialogTitle = stringResource(id = R.string.core_ds_read_more),
                color = Color.Unspecified,
                fontSize = TextUnit.Unspecified,
                fontStyle = null,
                fontWeight = null,
                fontFamily = null,
                letterSpacing = TextUnit.Unspecified,
                textDecoration = null,
                lineHeight = TextUnit.Unspecified,
                overflow = TextOverflow.Ellipsis,
                softWrap = true,
                maxLines = Int.MAX_VALUE,
                minLines = 1,
                maxCharacters = 200,
                onTextLayout = { _ -> },
                style = LocalTextStyle.current,
            )
        }
    }
}
