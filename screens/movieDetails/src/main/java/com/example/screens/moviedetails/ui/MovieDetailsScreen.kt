package com.example.screens.moviedetails.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.core.common.annotation.ExcludeFromGeneratedCoverageReport
import com.example.core.data.util.ImageUtil
import com.example.core.model.Movie
import com.example.core.model.names
import com.example.core.network.model.response.NetworkCast
import com.example.core.network.model.response.NetworkCrew
import com.example.core.network.model.response.NetworkGenre
import com.example.core.network.model.response.NetworkMovieCredit
import com.example.core.network.model.response.NetworkMovieDetails
import com.example.core.network.model.response.NetworkSpokenLanguage
import com.example.core.network.model.response.NetworkVideo
import com.example.core.network.model.response.NetworkVideos
import com.example.core.network.model.response.asDomainObject
import com.example.core.ui.component.GenreChip
import com.example.core.ui.component.MovieDetailsText
import com.example.core.ui.component.MoviePoster
import com.example.core.ui.component.PlayGroundCircularProgressIndicator
import com.example.core.ui.component.TrailerButton
import com.example.core.ui.widget.MovieRating
import com.example.core.ui.widget.PeoplePoster
import com.example.designSystem.core.annotation.ThemePreviews
import com.example.designSystem.core.theme.OneAndHalfVerticalSpacer
import com.example.designSystem.core.theme.OneVerticalSpacer
import com.example.designSystem.core.theme.PlayGround
import com.example.designSystem.core.theme.PlayGroundTheme
import com.example.designSystem.core.theme.ThreeVerticalSpacer
import com.example.designSystem.core.theme.TwoVerticalSpacer
import com.example.screens.moviedetails.R
import com.example.screens.moviedetails.viewmodel.MovieCreditUiState
import com.example.screens.moviedetails.viewmodel.MovieDetailsUiState
import com.example.screens.moviedetails.viewmodel.MovieDetailsViewModel
import com.example.screens.moviedetails.viewmodel.MoviesVideoUiState
import com.example.screens.moviedetails.viewmodel.SimilarMoviesUiState
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

const val MOVIE_DETAILS_COL = "movies_details_col"
const val MOVIE_DETAILS_OVERVIEW = "movies_details_overview"
const val MOVIE_DETAILS_TRAILER_BUTTON = "movies_details_trailer_button"
const val MOVIE_DETAILS_MAIN_CAST = "movies_details_main_cast"
const val MOVIE_DETAILS_MAIN_CAST_ROW = "movies_details_main_cast_row"
const val MOVIE_DETAILS_CREW = "movies_details_bottom_crew"
const val MOVIE_DETAILS_CREW_ROW = "movies_details_bottom_crew_row"
const val MOVIE_DETAILS_CATEGORIES = "movies_details_categories"
const val MOVIE_DETAILS_CATEGORIES_ROW = "movies_details_categories_row"
const val MOVIE_DETAILS_RECOMMENDATIONS = "movies_details_recommendations"
const val MOVIE_DETAILS_RECOMMENDATIONS_ROW = "movies_details_recommendations_row"
const val MOVIE_DETAILS_BOTTOM_BAR = "movies_details_bottom_bar"

private const val ONE_HOUR_IN_MINUTES = 60
private const val MAX_MOVIE_RATING = 10.0

@Composable
@Suppress("UnusedPrivateMember")
@ExcludeFromGeneratedCoverageReport
fun MoviesDetailsScreen(
    viewModel: MovieDetailsViewModel = hiltViewModel(),
    onMovieItemClick: (Long) -> Unit,
    onWatchTrailerClick: () -> Unit = { },
    onBackPress: () -> Unit,
) {
    val movieDetailsUiState by viewModel.movieDetailsUiState.collectAsStateWithLifecycle()
    val movieCreditUiState by viewModel.movieCreditUiState.collectAsStateWithLifecycle()
    val similarMoviesUiState by viewModel.similarMoviesUiState.collectAsStateWithLifecycle()
    val moviesVideoUiState by viewModel.moviesVideoUiState.collectAsStateWithLifecycle()

    val onAddToWatchListClick: () -> Unit = {}
    val onAddToBookmarkClick: () -> Unit = {}
    val onAddToFavorite: () -> Unit = {}
    val onRateItClick: () -> Unit = {}

    MoviesDetailsScreen(
        movieDetailsUiState = movieDetailsUiState,
        movieCreditUiState = movieCreditUiState,
        similarMoviesUiState = similarMoviesUiState,
        moviesVideoUiState = moviesVideoUiState,
        onAddToWatchListClick = onAddToWatchListClick,
        onAddToBookmarkClick = onAddToBookmarkClick,
        onAddToFavorite = onAddToFavorite,
        onRateItClick = onRateItClick,
        onWatchTrailerClick = onWatchTrailerClick,
        onMovieItemClick = onMovieItemClick,
        onNavigateUp = onBackPress,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Suppress("UnusedPrivateMember", "CyclomaticComplexMethod")
fun MoviesDetailsScreen(
    movieDetailsUiState: MovieDetailsUiState,
    movieCreditUiState: MovieCreditUiState,
    similarMoviesUiState: SimilarMoviesUiState,
    moviesVideoUiState: MoviesVideoUiState,
    onAddToWatchListClick: () -> Unit = {},
    onAddToBookmarkClick: () -> Unit = {},
    onAddToFavorite: () -> Unit = {},
    onRateItClick: () -> Unit = {},
    onWatchTrailerClick: () -> Unit = { },
    onMovieItemClick: (Long) -> Unit = {},
    onNavigateUp: () -> Unit = {},
) {
    var showBottomAppBar by remember { mutableStateOf(false) }
    val primaryTextColor = contentColorFor(PlayGround.color.inverseOnSurface)
    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.Window)

    @Suppress("MagicNumber")
    val secondaryTextColor = primaryTextColor.copy(0.7F)
    val state = rememberCollapsingToolbarScaffoldState()
    val enabled by remember { mutableStateOf(true) }
    val colorStops = listOf(
        Color(0x00000000),
        PlayGround.color.inverseOnSurface.copy(alpha = 0.1F),
        PlayGround.color.inverseOnSurface.copy(alpha = 0.5F),
        PlayGround.color.inverseOnSurface.copy(alpha = 0.8F),
        PlayGround.color.inverseOnSurface,
    )

    val colorStops2 = listOf(
        PlayGround.color.inverseOnSurface.copy(alpha = 0.5F),
        PlayGround.color.inverseOnSurface.copy(alpha = 0.8F),
        PlayGround.color.inverseOnSurface.copy(alpha = 0.9F),
        PlayGround.color.inverseOnSurface,
    )

    fun formatRuntime(runtime: Int): String {
        val hours = runtime / ONE_HOUR_IN_MINUTES
        val minutes = runtime.rem(ONE_HOUR_IN_MINUTES)
        return "${hours}${if (hours > 1) "hr(s)" else "hr"} ${minutes}m"
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (movieDetailsUiState) {
            is MovieDetailsUiState.Success -> {
                val movieDetails = movieDetailsUiState.movieDetails

                var isLoading by remember { mutableStateOf(true) }
                var isError by remember { mutableStateOf(false) }
                val isLocalInspection = LocalInspectionMode.current

                val posterImageLoader = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(ImageUtil.buildImageUrl(movieDetails.posterPath))
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.FillBounds,
                    onState = { state ->
                        isLoading = state is AsyncImagePainter.State.Loading
                        isError = state is AsyncImagePainter.State.Error
                    },
                )

                CollapsingToolbarScaffold(
                    modifier = Modifier.fillMaxSize(),
                    state = state,
                    scrollStrategy = ScrollStrategy.EnterAlways,
                    toolbarModifier = Modifier.background(PlayGround.color.primary),
                    enabled = enabled,
                    toolbar = {
                        // Collapsing toolbar collapses its size as small as the that of
                        // a smallest child. To make the toolbar collapse to 50dp, we create
                        // a dummy Spacer composable.
                        // You may replace it with TopAppBar or other preferred composable.
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                        )

                        @Suppress("MagicNumber")
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .parallax(0.5f)
                                .height(450.dp)
                                .fillMaxWidth(),
                        ) {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .graphicsLayer {
                                        alpha = state.toolbarState.progress
                                    },
                                painter = if (isError.not() && !isLocalInspection) {
                                    posterImageLoader
                                } else {
                                    painterResource(com.example.core.ui.R.drawable.sample_poster)
                                },
                                contentDescription = movieDetails.title,
                                contentScale = ContentScale.FillWidth,
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Brush.verticalGradient(colorStops)),
                            )

                            Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = PlayGround.spacing.two),
                                    horizontalArrangement = Arrangement.spacedBy(
                                        PlayGround.spacing.two,
                                    ),
                                ) {
                                    MovieRating(
                                        modifier = Modifier.size(57.dp),
                                        rating = (movieDetails.voteAverage / MAX_MOVIE_RATING)
                                            .toFloat(),
                                    )
                                    Column(
                                        horizontalAlignment = Alignment.Start,
                                        verticalArrangement = Arrangement.spacedBy(
                                            PlayGround.spacing.half,
                                        ),
                                    ) {
                                        Text(
                                            text = movieDetails.title,
                                            style = PlayGround.typography.titleLarge,
                                            color = primaryTextColor,
                                        )
                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(
                                                PlayGround.spacing.one,
                                            ),
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            Text(
                                                text = movieDetails.releaseDate.formatDate(),
                                                color = secondaryTextColor,
                                            )
                                            Text(text = "●", color = secondaryTextColor)
                                            Icon(
                                                painter = painterResource(
                                                    id = com.example.core.ui.R.drawable.ic_clock,
                                                ),
                                                contentDescription = "runtime",
                                                tint = secondaryTextColor,
                                            )
                                            Text(
                                                text = formatRuntime(movieDetails.runtime),
                                                color = secondaryTextColor,
                                            )
                                        }
                                    }
                                }

                                TwoVerticalSpacer()
                                HorizontalDivider(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(secondaryTextColor.copy(alpha = 0.1F)),
                                )
                            }

                            // Fancy top nav bar
                            Row(
                                modifier = Modifier.statusBarsPadding(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(
                                    PlayGround.spacing.two,
                                ),
                            ) {
                                Surface(
                                    modifier = Modifier.padding(PlayGround.spacing.one),
                                    color = Color.LightGray.copy(alpha = 0.3F),
                                    shape = CircleShape,
                                ) {
                                    IconButton(onClick = onNavigateUp) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                            contentDescription = stringResource(
                                                id = com.example.core.ui.R.string.navigate_up,
                                            ),
                                        )
                                    }
                                }
                                Text(movieDetails.title, style = PlayGround.typography.titleLarge)
                            }
                        }
                    },
                ) {
                    val scrollState = rememberLazyListState()

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .matchParentSize()
                            .background(Brush.verticalGradient(colorStops2))
                            .testTag(MOVIE_DETAILS_COL),
                        state = scrollState,
                    ) {
                        item {
                            TwoVerticalSpacer()
                            MovieDetailsText(
                                modifier = Modifier
                                    .testTag(MOVIE_DETAILS_OVERVIEW),
                                text = movieDetails.overview,
                                dialogTitle = movieDetails.title,
                                style = PlayGround.typography.bodyMedium,
                                textAlign = TextAlign.Justify,
                                color = secondaryTextColor,
                            )
                        }

                        // Trailer button
                        when (moviesVideoUiState) {
                            is MoviesVideoUiState.Success -> {
                                val videos = moviesVideoUiState.videos
                                if (videos.isNotEmpty()) {
                                    item {
                                        ThreeVerticalSpacer()
                                        TrailerButton(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .testTag(MOVIE_DETAILS_TRAILER_BUTTON)
                                                .padding(horizontal = PlayGround.spacing.two),
                                            onClick = { onWatchTrailerClick() },
                                        )
                                    }
                                }
                            }

                            else -> {
                                item { TwoVerticalSpacer() }
                            }
                        }

                        // Main cast
                        item {
                            ThreeVerticalSpacer()
                            Text(
                                modifier = Modifier
                                    .testTag(MOVIE_DETAILS_MAIN_CAST)
                                    .padding(horizontal = PlayGround.spacing.two),
                                style = PlayGround.typography.titleMedium,
                                color = contentColorFor(
                                    backgroundColor = PlayGround.color.inverseOnSurface,
                                ),
                                text = stringResource(R.string.title_main_cast),
                            )
                            OneVerticalSpacer()
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag(MOVIE_DETAILS_MAIN_CAST_ROW)
                                    .padding(horizontal = PlayGround.spacing.two),
                                horizontalArrangement =
                                    if (movieCreditUiState is MovieCreditUiState.Success) {
                                        Arrangement.spacedBy(PlayGround.spacing.one)
                                    } else {
                                        Arrangement.Center
                                    },
                            ) {
                                when (movieCreditUiState) {
                                    is MovieCreditUiState.LoadFailed -> {
                                        item {
                                            MovieDetailsLoadFailed(
                                                message = "Error, please try again!",
                                                onClick = onNavigateUp,
                                            )
                                        }
                                    }

                                    MovieCreditUiState.Loading -> {
                                        item { PlayGroundCircularProgressIndicator() }
                                    }

                                    is MovieCreditUiState.Success -> {
                                        items(
                                            movieCreditUiState.movieCredit.cast
                                                .distinctBy { it.name },
                                        ) {
                                            val (firstName, lastName) = it.names()
                                            PeoplePoster(
                                                size = 80.dp,
                                                firstName = firstName,
                                                lastName = lastName,
                                                imageUrl = ImageUtil.buildImageUrl(
                                                    path = it.profilePath ?: "",
                                                    size = ImageUtil.Size.Profile.H632,
                                                ),
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Crew
                        item {
                            ThreeVerticalSpacer()
                            Text(
                                modifier = Modifier
                                    .testTag(MOVIE_DETAILS_CREW)
                                    .padding(horizontal = PlayGround.spacing.two),
                                style = PlayGround.typography.titleMedium,
                                color = contentColorFor(
                                    backgroundColor = PlayGround.color.inverseOnSurface,
                                ),
                                text = stringResource(R.string.title_crew),
                            )
                            OneVerticalSpacer()
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag(MOVIE_DETAILS_CREW_ROW)
                                    .padding(horizontal = PlayGround.spacing.two),
                                horizontalArrangement =
                                    if (movieCreditUiState is MovieCreditUiState.Success) {
                                        Arrangement.spacedBy(PlayGround.spacing.one)
                                    } else {
                                        Arrangement.Center
                                    },
                            ) {
                                when (movieCreditUiState) {
                                    is MovieCreditUiState.LoadFailed -> {
                                        item {
                                            MovieDetailsLoadFailed(
                                                message = "Error, please try again!",
                                                onClick = onNavigateUp,
                                            )
                                        }
                                    }

                                    MovieCreditUiState.Loading -> {
                                        item { PlayGroundCircularProgressIndicator() }
                                    }

                                    is MovieCreditUiState.Success -> {
                                        items(
                                            movieCreditUiState.movieCredit.crew
                                                .distinctBy { it.name },
                                        ) {
                                            val (firstName, lastName) = it.names()
                                            PeoplePoster(
                                                size = 80.dp,
                                                firstName = firstName,
                                                lastName = lastName,
                                                imageUrl = ImageUtil.buildImageUrl(
                                                    path = it.profilePath ?: "",
                                                    size = ImageUtil.Size.Profile.W185,
                                                ),
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Categories
                        item {
                            ThreeVerticalSpacer()
                            Text(
                                modifier = Modifier
                                    .testTag(MOVIE_DETAILS_CATEGORIES)
                                    .padding(horizontal = PlayGround.spacing.two),
                                style = PlayGround.typography.titleMedium,
                                color = contentColorFor(
                                    backgroundColor = PlayGround.color.inverseOnSurface,
                                ),
                                text = stringResource(R.string.title_categories),
                            )
                            OneAndHalfVerticalSpacer()
                            FlowRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag(MOVIE_DETAILS_CATEGORIES_ROW)
                                    .padding(horizontal = PlayGround.spacing.two),
                                horizontalArrangement = Arrangement.spacedBy(
                                    PlayGround.spacing.one,
                                ),
                                verticalArrangement = Arrangement.spacedBy(PlayGround.spacing.one),
                            ) {
                                (movieDetails.genres.map { genre -> genre.name })
                                    .forEach { GenreChip(text = it) }
                            }
                        }

                        // recommendations
                        item {
                            ThreeVerticalSpacer()
                            Text(
                                modifier = Modifier
                                    .testTag(MOVIE_DETAILS_RECOMMENDATIONS)
                                    .padding(horizontal = PlayGround.spacing.two),
                                style = PlayGround.typography.titleMedium,
                                color = contentColorFor(
                                    backgroundColor = PlayGround.color.inverseOnSurface,
                                ),
                                text = stringResource(R.string.title_recommendations),
                            )
                            OneVerticalSpacer()
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag(MOVIE_DETAILS_RECOMMENDATIONS_ROW)
                                    .padding(horizontal = PlayGround.spacing.two),
                                horizontalArrangement =
                                    if (similarMoviesUiState is SimilarMoviesUiState.Success) {
                                        Arrangement.spacedBy(PlayGround.spacing.one)
                                    } else {
                                        Arrangement.Center
                                    },
                            ) {
                                when (similarMoviesUiState) {
                                    is SimilarMoviesUiState.LoadFailed -> {
                                        item {
                                            MovieDetailsLoadFailed(
                                                message = "Error, please try again!",
                                                onClick = onNavigateUp,
                                            )
                                        }
                                    }

                                    SimilarMoviesUiState.Loading -> {
                                        item { PlayGroundCircularProgressIndicator() }
                                    }

                                    is SimilarMoviesUiState.Success -> {
                                        items(similarMoviesUiState.movies) {
                                            val path = ImageUtil.buildImageUrl(it.posterPath)
                                            @Suppress("MagicNumber")
                                            MoviePoster(
                                                modifier = Modifier
                                                    .width(120.dp)
                                                    .aspectRatio(0.7F),
                                                posterUrl = path,
                                                contentDescription = it.title,
                                                shimmer = shimmer,
                                                onClick = { onMovieItemClick(it.id) },
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Bottom padding
                        item {
                            Spacer(modifier = Modifier.height(130.0.dp))
                        }
                    }
                }
            }

            is MovieDetailsUiState.LoadFailed -> {
                MovieDetailsLoadFailed(
                    modifier = Modifier.align(Alignment.Center),
                    message = movieDetailsUiState.message,
                    onClick = onNavigateUp,
                )
            }

            MovieDetailsUiState.Loading -> {
                PlayGroundCircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = showBottomAppBar,
            enter = slideInVertically() + expandHorizontally(expandFrom = Alignment.End),
            exit = fadeOut(),
        ) {
            BottomAppBar(
                modifier = Modifier.testTag(MOVIE_DETAILS_BOTTOM_BAR),
                actions = {
                    IconButton(onClick = onAddToWatchListClick) {
                        Icon(
                            imageVector = Icons.Filled.List,
                            contentDescription = stringResource(R.string.add_to_watch_list),
                        )
                    }
                    IconButton(onClick = onAddToBookmarkClick) {
                        Icon(
                            painterResource(
                                id = com.example.core.ui.R.drawable.baseline_bookmark_add_24,
                            ),
                            contentDescription = stringResource(R.string.add_to_bookmark),
                        )
                    }
                    IconButton(onClick = onRateItClick) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = stringResource(R.string.rate_it),
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = onAddToFavorite,
                        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.FavoriteBorder,
                            contentDescription = stringResource(R.string.add_to_favourite),
                        )
                    }
                },
            )
        }
    }

    LaunchedEffect(Unit) {
        showBottomAppBar = true
    }
}

private fun String.formatDate(): String {
    return this.replace("-", "/")
}

@ThemePreviews
@Composable
@ExcludeFromGeneratedCoverageReport
fun MoviesDetailsPreview() {
    val testMovieCredit = testNetworkMovieCredit(0L).asDomainObject()
    val movieDetailsUiState = MovieDetailsUiState.Success(
        movieDetails = testNetworkMovieDetails(0L).asDomainObject(),
    )
    val movieCreditUiState = MovieCreditUiState.Success(movieCredit = testMovieCredit)
    val similarMoviesUiState = SimilarMoviesUiState.Success(movies = testMovies())

    val moviesVideoUiState = MoviesVideoUiState.Success(
        videos = testNetworkMovieVideos(
            movieId = 0L,
        ).results.map { it.asDomainObject() },
        0L,
    )

    PlayGroundTheme {
        MoviesDetailsScreen(
            movieDetailsUiState = movieDetailsUiState,
            movieCreditUiState = movieCreditUiState,
            similarMoviesUiState = similarMoviesUiState,
            moviesVideoUiState = moviesVideoUiState,
            onMovieItemClick = {},
        ) {}
    }
}

@Composable
private fun MovieDetailsLoadFailed(
    modifier: Modifier = Modifier,
    message: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = message)
        TwoVerticalSpacer()
        TextButton(modifier = modifier, onClick = onClick) {
            Text(text = "Back")
        }
    }
}

@ExcludeFromGeneratedCoverageReport
private fun testNetworkMovieDetails(movieId: Long): NetworkMovieDetails = NetworkMovieDetails(
    adult = false,
    backdropPath = "/hZkgoQYus5vegHoetLkCJzb17zJ.jpg",
    budget = 63000000,
    genres =
        listOf(
            NetworkGenre(id = 18, name = "Drama"),
            NetworkGenre(id = 53, name = "Thriller"),
            NetworkGenre(id = 35, name = "Comedy"),
        ),
    homepage = "http://www.foxmovies.com/movies/fight-club",
    id = movieId,
    imdbId = "tt0137523",
    originalLanguage = "en",
    originalTitle = "Fight Club",
    overview =
        """
            A ticking-time-bomb insomniac and a slippery soap salesman channel primal male
             aggression into a shocking new form of therapy. Their concept catches on, with 
             underground \"fight clubs\" forming in every town, until an eccentric gets in the 
             way and ignites an out-of-control spiral toward oblivion.
        """.trimIndent(),
    popularity = 61.416,
    posterPath = "/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
    releaseDate = "1999-10-15",
    revenue = 100853753,
    runtime = 139,
    spokenLanguages =
        listOf(
            NetworkSpokenLanguage(
                englishName = "English",
                iso6391 = "en",
                name = "English",
            ),
        ),
    status = "Released",
    tagline = "Mischief. Mayhem. Soap.",
    title = "Fight Club",
    video = false,
    voteAverage = 8.433,
    voteCount = 26280,
)

@ExcludeFromGeneratedCoverageReport
private fun testNetworkMovieCredit(movieId: Long): NetworkMovieCredit = NetworkMovieCredit(
    id = movieId.toInt(),
    cast = listOf(
        NetworkCast(
            adult = false,
            castId = 4,
            character = "The Narrator",
            creditId = "52fe4250c3a36847f80149f3",
            gender = 2,
            id = 819,
            knownForDepartment = "Acting",
            name = "Edward Norton",
            order = 0,
            originalName = "Edward Norton",
            popularity = 26.99,
            profilePath = "/huV2cdcolEUwJy37QvH914vup7d.jpg",
        ),
        NetworkCast(
            adult = false,
            castId = 0,
            character = "Tyler Durden",
            creditId = "52fe4250c3a36847f80149f7",
            gender = 1,
            id = 1283,
            knownForDepartment = "Acting",
            name = "Helena Bonham Carter",
            order = 2,
            originalName = "Helena Bonham Carter",
            popularity = 22.112,
            profilePath = "/DDeITcCpnBd0CkAIRPhggy9bt5.jpg",
        ),
    ),
    crew =
        listOf(
            NetworkCrew(
                adult = false,
                creditId = "55731b8192514111610027d7",
                department = "Production",
                gender = 2,
                id = 376,
                job = "Executive Producer",
                knownForDepartment = "Production",
                name = "Arnon Milchan",
                originalName = "Arnon Milchan",
                popularity = 2.931,
                profilePath = "/b2hBExX4NnczNAnLuTBF4kmNhZm.jpg",
            ),
            NetworkCrew(
                adult = false,
                creditId = "5894c4eac3a3685ec6000218",
                department = "Costume & Make-Up",
                gender = 2,
                id = 605,
                job = "Costume Design",
                knownForDepartment = "Costume & Make-Up",
                name = "Michael Kaplan",
                originalName = "Michael Kaplan",
                popularity = 4.294,
                profilePath = "/bNarnI5K4XYIKaHsX6HAitllyQr.jpg",
            ),
        ),
)

@ExcludeFromGeneratedCoverageReport
private fun testNetworkMovieVideos(movieId: Long): NetworkVideos = NetworkVideos(
    id = movieId.toInt(),
    results = listOf(
        NetworkVideo(
            id = "639d5326be6d88007f170f44",
            iso31661 = "en",
            iso6391 = "US",
            key = "O-b2VfmmbyA",
            name = """
                Fight Club (1999) Trailer - Starring Brad Pitt, Edward Norton, 
                Helena Bonham Carter
            """.trimIndent(),
            official = false,
            publishedAt = "2016-03-05T02:03:14.000Z",
            site = "YouTube",
            size = 720,
            type = "Trailer",
        ),
        NetworkVideo(
            id = "5c9294240e0a267cd516835f",
            iso31661 = "en",
            iso6391 = "US",
            key = "BdJKm16Co6M",
            name = "#TBT Trailer",
            official = false,
            publishedAt = "2014-10-02T19:20:22.000Z",
            site = "YouTube",
            size = 1080,
            type = "Trailer",
        ),
    ),
)

@ExcludeFromGeneratedCoverageReport
private fun testMovies(): List<Movie> = listOf(
    Movie(
        id = 667538,
        title = "Transformers: Rise of the Beasts",
        overview =
            """
            When a new threat capable of destroying the entire planet emerges, Optimus Prime and 
            the Autobots must team up with a powerful faction known as the Maximals. With the 
            fate of humanity hanging in the balance, humans Noah and Elena will do whatever it 
            takes to help the Transformers as they engage in the ultimate battle to save Earth.
            """.trimIndent(),
        backdropPath = "/bz66a19bR6BKsbY8gSZCM4etJiK.jpg",
        posterPath = "/2vFuG6bWGyQUzYS9d69E5l85nIz.jpg",
        voteAverage = 7.5,
    ),
    Movie(
        id = 298618,
        title = "The Flash",
        overview =
            """
            When his attempt to save his family inadvertently alters the future, 
            Barry Allen becomes trapped in a reality in which General Zod has returned and 
            there are no Super Heroes to turn to. In order to save the world that he is in and 
            return to the future that he knows, Barry's only hope is to race for his life. But 
            will making the ultimate sacrifice be enough to reset the universe
            """.trimIndent(),
        backdropPath = "/yF1eOkaYvwiORauRCPWznV9xVvi.jpg",
        posterPath = "/rktDFPbfHfUbArZ6OOOKsXcv0Bm.jpg",
        voteAverage = 7.0,
    ),
    Movie(
        id = 884605,
        title = "No Hard Feelings",
        overview =
            """
            On the brink of losing her childhood home, Maddie discovers an intriguing job listing: 
            wealthy helicopter parents looking for someone to “date” their introverted 19-year-old 
            son, Percy, before he leaves for college. To her surprise, Maddie soon discovers the 
            awkward Percy is no sure thing.
            """.trimIndent(),
        backdropPath = "/rRcNmiH55Tz0ugUsDUGmj8Bsa4V.jpg",
        posterPath = "/4K7gQjD19CDEPd7A9KZwr2D9Nco.jpg",
        voteAverage = 7.1,
    ),
    Movie(
        id = 346698,
        title = "Barbie",
        overview =
            """
            Barbie and Ken are having the time of their lives in the colorful and 
            seemingly perfect world of Barbie Land. However, when they get a chance 
            to go to the real world, they soon discover the joys and perils of 
            living among humans.
            """.trimIndent(),
        backdropPath = "/nHf61UzkfFno5X1ofIhugCPus2R.jpg",
        posterPath = "/iuFNMS8U5cb6xfzi51Dbkovj7vM.jpg",
        voteAverage = 7.5,
    ),
)
