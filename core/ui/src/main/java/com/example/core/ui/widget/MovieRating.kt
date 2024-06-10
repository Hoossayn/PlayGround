package com.example.core.ui.widget

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.core.common.annotation.ExcludeFromGeneratedCoverageReport
import com.example.design_system.core.annotation.ThemePreviews
import com.example.design_system.core.theme.PlayGround
import com.example.design_system.core.theme.PlayGroundTheme

@Composable
fun MovieRating(
    modifier: Modifier = Modifier,
    rating: Float,
    strokeWidth: Dp = 6.dp,
    textStyle: TextStyle = PlayGround.typography.titleSmall,
    textColor: Color = contentColorFor(backgroundColor = PlayGround.color.inverseOnSurface),
) {
    var progress by remember { mutableStateOf(0f) }
    val progressAnimation by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = 3000,
            delayMillis = 100,
            easing = FastOutSlowInEasing,
        ),
        label = "progressAnimation",
    )

    Box(modifier = modifier) {
        CircularProgressIndicator(
            progress = { progressAnimation },
            modifier = Modifier.fillMaxSize(),
            color = PlayGround.color.secondaryContainer,
            strokeWidth = strokeWidth,
            trackColor = PlayGround.color.tertiaryContainer,
            strokeCap = StrokeCap.Round,
        )
        @Suppress("MagicNumber")
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "${(rating * 100).toInt()}%",
            textAlign = TextAlign.Center,
            style = textStyle,
            fontWeight = FontWeight.W700,
            color = textColor,
        )
    }

    LaunchedEffect(rating) {
        progress = rating
    }
}

@ThemePreviews
@Composable
@ExcludeFromGeneratedCoverageReport
fun MovieRatingPreview() {
    PlayGroundTheme {
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
