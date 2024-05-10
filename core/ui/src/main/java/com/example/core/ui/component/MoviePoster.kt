package com.example.core.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.core.common.annotation.ExcludeFromGeneratedCoverageReport
import com.example.core.ui.R
import com.example.design_system.core.annotation.ThemePreviews
import com.example.design_system.core.theme.PlayGround
import com.example.design_system.core.theme.PlayGroundTheme
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer


const val MOVIE_POSTER_TEST_TAG = "movie_poster"
private const val SIZE_ASPECT_RATIO = 0.8F

@Composable
fun MoviePoster(
    modifier: Modifier = Modifier,
    posterUrl: String?,
    contentDescription: String,
    shimmer: Shimmer,
    onClick: () -> Unit,
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    val isLocalInspection = LocalInspectionMode.current

    val imageLoader = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(posterUrl)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.FillBounds,
        onState = { state ->
            isLoading = state is AsyncImagePainter.State.Loading
            isError = state is AsyncImagePainter.State.Error
        },
    )

    Card(
        modifier = modifier.testTag(MOVIE_POSTER_TEST_TAG),
        onClick = onClick,
        shape = PlayGround.shape.small,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        ShimmerBox(
            modifier = Modifier.fillMaxSize(),
            shimmer = shimmer,
            isLoading = isLoading && !isError,
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = if (isError.not() && !isLocalInspection) {
                    imageLoader
                } else {
                    painterResource(R.drawable.sample_poster)
                },
                contentScale = ContentScale.FillBounds,
                contentDescription = contentDescription,
            )
        }
    }
}

@ThemePreviews
@Composable
@ExcludeFromGeneratedCoverageReport
fun PosterPreview() {
    PlayGroundTheme {
        Column(
            modifier = Modifier.padding(PlayGround.spacing.twoAndaHalf),
        ) {
            MoviePoster(
                modifier =
                Modifier
                    .width(100.dp)
                    .aspectRatio(SIZE_ASPECT_RATIO),
                posterUrl = "https://example.com/image.jpg",
                contentDescription = "Image",
                shimmer =
                rememberShimmer(
                    shimmerBounds = ShimmerBounds.Window,
                ),
            ) {}
        }
    }
}

