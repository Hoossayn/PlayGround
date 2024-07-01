package com.example.core.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
fun PersonItemViewPreview() {
    PlayGroundTheme {
        Column(
            modifier =
                Modifier
                    .wrapContentSize()
                    .background(PlayGround.color.surface),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OneVerticalSpacer()
            PersonItemView(
                modifier =
                    Modifier
                        .width(250.dp)
                        .padding(PlayGround.spacing.twoAndaHalf),
                name = "Sandra Bullock",
                knownFor = "Rush Hour, Rush Hour 2, and Rush Hour 3",
                imageUrl = "",
            )
            OneVerticalSpacer()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonItemView(
    modifier: Modifier = Modifier,
    name: String,
    knownFor: String,
    imageUrl: String,
    textColor: Color = contentColorFor(backgroundColor = PlayGround.color.surfaceVariant),
    onClick: () -> Unit = {},
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

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(5.dp),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1F),
            ) {
                val contentDescription = "$name's profile"
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(5.dp)),
                    contentScale = ContentScale.FillWidth,
                    painter = if (isError.not() && !isLocalInspection) {
                        imageLoader
                    } else {
                        painterResource(R.drawable.ic_avatar)
                    },
                    contentDescription = contentDescription,
                )

                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .matchParentSize()
                            .align(Alignment.Center)
                            .padding(PlayGround.spacing.three),
                        strokeCap = StrokeCap.Round,
                        strokeWidth = 1.dp,
                    )
                }
            }

            OneVerticalSpacer()
            Column(modifier = Modifier.padding(horizontal = PlayGround.spacing.one)) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = name,
                    style = PlayGround.typography.titleMedium,
                    maxLines = 1,
                    color = textColor,
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = knownFor,
                    style = PlayGround.typography.bodySmall,
                    maxLines = 2,
                    minLines = 2,
                    color = textColor.copy(alpha = 0.7f),
                )
                OneVerticalSpacer()
            }
        }
    }
}
