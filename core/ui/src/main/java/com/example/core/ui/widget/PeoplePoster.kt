package com.example.core.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.core.common.annotation.ExcludeFromGeneratedCoverageReport
import com.example.core.ui.R
import com.example.designSystem.core.annotation.ThemePreviews
import com.example.designSystem.core.theme.OneVerticalSpacer
import com.example.designSystem.core.theme.PlayGround
import com.example.designSystem.core.theme.PlayGroundTheme

@ThemePreviews
@Composable
@ExcludeFromGeneratedCoverageReport
fun PeoplePosterPreview() {
    PlayGroundTheme {
        Column(
            modifier =
                Modifier
                    .size(200.dp)
                    .aspectRatio(1F)
                    .background(PlayGround.color.inverseOnSurface),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            PeoplePoster(
                modifier = Modifier,
                size = 120.dp,
                firstName = "Sandra",
                lastName = "Bullock",
                imageUrl = "",
            )
        }
    }
}

@Composable
fun PeoplePoster(
    modifier: Modifier = Modifier,
    size: Dp = 60.dp,
    firstName: String,
    lastName: String,
    imageUrl: String,
    textColor: Color = contentColorFor(backgroundColor = PlayGround.color.inverseOnSurface),
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    val isLocalInspection = LocalInspectionMode.current

    val imageLoader = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.FillWidth,
        onState = { state ->
            isLoading = state is AsyncImagePainter.State.Loading
            isError = state is AsyncImagePainter.State.Error
        },
    )

    Column(
        modifier = modifier
            .semantics(mergeDescendants = true) {
                contentDescription = "$firstName $lastName"
            }
            .width(size),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = modifier
                .size(size)
                .aspectRatio(1F),
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .border(
                        width = 3.dp,
                        color = PlayGround.color.tertiaryContainer.copy(alpha = 0.5F),
                        shape = CircleShape,
                    )
                    .clip(CircleShape),
                painter = if (isError.not() && !isLocalInspection) {
                    imageLoader
                } else {
                    painterResource(R.drawable.ic_avatar)
                },
                contentScale = ContentScale.Inside,
                contentDescription = null,
            )

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center)
                        .padding(PlayGround.spacing.twoAndaHalf),
                    strokeCap = StrokeCap.Round,
                    strokeWidth = 1.dp,
                )
            }
        }
        OneVerticalSpacer()
        Column(
            modifier = Modifier.height(34.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = firstName,
                style = PlayGround.typography.labelMedium,
                color = textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
            )

            if (lastName.isNotBlank()) {
                Text(
                    text = lastName,
                    style = PlayGround.typography.labelMedium,
                    color = textColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
