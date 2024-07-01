package com.example.core.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.example.core.common.annotation.ExcludeFromGeneratedCoverageReport
import com.example.core.ui.R
import com.example.designSystem.core.annotation.ThemePreviews
import com.example.designSystem.core.component.PlayGroundButton
import com.example.designSystem.core.theme.OneHorizontalSpacer
import com.example.designSystem.core.theme.PlayGround
import com.example.designSystem.core.theme.PlayGroundTheme

@Composable
fun MovieDetailsText(
    text: String,
    modifier: Modifier = Modifier,
    dialogTitle: String = stringResource(id = R.string.core_ds_read_more),
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    maxCharacters: Int = 200,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    style: TextStyle = LocalTextStyle.current,
) {
    val shouldShowReadMore = text.length > maxCharacters
    var showMore by remember { mutableStateOf(false) }
    val shortText by rememberSaveable(text) {
        mutableStateOf(
            if (shouldShowReadMore) {
                text.take(
                    maxCharacters,
                )
            } else {
                text
            },
        )
    }

    Box(modifier = Modifier.wrapContentSize()) {
        Text(
            text = shortText,
            modifier = modifier.padding(
                start = PlayGround.spacing.one,
                end = PlayGround.spacing.one,
                top = PlayGround.spacing.one,
                bottom =
                    if (shouldShowReadMore) PlayGround.spacing.six else PlayGround.spacing.one),
            color = color,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            minLines = minLines,
            onTextLayout = onTextLayout,
            style = style,
        )

        if (shouldShowReadMore) {
            Box(
                Modifier
                    .matchParentSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                PlayGround.color.inverseOnSurface.copy(alpha = 0.1F),
                                PlayGround.color.inverseOnSurface.copy(alpha = 0.5F),
                                PlayGround.color.inverseOnSurface.copy(alpha = 0.9F),
                                PlayGround.color.inverseOnSurface,
                            ),
                        ),
                    ),
            )
        }
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            visible = shouldShowReadMore,
        ) {
            PlayGroundButton(
                modifier = Modifier
                    .padding(bottom = PlayGround.spacing.two),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PlayGround.color.surfaceContainerHigh,
                    contentColor = contentColorFor(PlayGround.color.surfaceContainerHigh),
                ),
                onClick = { showMore = true },
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(R.string.core_ds_read_more))
                    OneHorizontalSpacer()
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = stringResource(
                            id = R.string.core_ds_read_more,
                        ),
                    )
                }
            }
        }

        if (showMore) {
            AlertDialog(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = stringResource(
                            id = R.string.core_ds_read_more,
                        ),
                    )
                },
                title = {
                    Text(text = dialogTitle)
                },
                text = {
                    Text(text = text)
                },
                onDismissRequest = {
                    showMore = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showMore = false
                        },
                    ) {
                        Text(stringResource(R.string.core_ds_ok))
                    }
                },
            )
        }
    }
}

@ThemePreviews
@Composable
@ExcludeFromGeneratedCoverageReport
fun MovieDetailsTextPreview() {
    PlayGroundTheme {
        val text =
            "Terrible Script, dialogue, directing, hammy editing. Music is meh. Poor acting" +
                " (but they clearly got no support). This crap show's the contempt " +
                "producers like this have for the audience that gives them money. " +
                "The writer, director, producers and editor must have been so full of " +
                "ego that I bet continues today, despite the poor reviews. During the " +
                "boring minutes (90) I drifted off imagining them all working on these " +
                "scenes thinking they were amazing, and trying to understand the level " +
                "of narcissism required. Just delusional film makers that like the " +
                "smell of each others farts. During the credits I imagined the director " +
                "is still down there and that's what got it 0.5 rather than 0."
        MovieDetailsText(text = text, textAlign = TextAlign.Justify)
    }
}
