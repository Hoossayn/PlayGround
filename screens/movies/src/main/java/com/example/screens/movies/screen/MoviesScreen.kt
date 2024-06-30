package com.example.screens.movies.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core.common.annotation.ExcludeFromGeneratedCoverageReport
import com.example.core.data.util.ImageUtil
import com.example.core.model.Movie
import com.example.core.model.MovieListFilterItem
import com.example.core.ui.component.Background
import com.example.core.ui.component.MovieListFilter
import com.example.core.ui.component.MoviePoster
import com.example.designSystem.core.annotation.ThemePreviews
import com.example.designSystem.core.theme.FillingSpacer
import com.example.designSystem.core.theme.FiveVerticalSpacer
import com.example.designSystem.core.theme.PlayGround
import com.example.designSystem.core.theme.PlayGroundTheme
import com.example.designSystem.core.theme.TwoVerticalSpacer
import com.example.screens.movies.R
import com.example.screens.movies.viewmodel.MoviesViewModel
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import kotlinx.coroutines.flow.flowOf

const val TITLE_TEST_TAG = "title"
const val CHIP_GROUP_TEST_TAG = "chips"
const val MOVIES_GRID_ITEMS_TEST_TAG = "movies_gridItems"
const val SEARCH_TEST_TAG = "search"
const val ASPECT_RATIO = 0.7F
const val FRESH_LOAD_PROGRESS_TEST_TAG = "fresh_load"
const val APPEND_LOAD_PROGRESS_TEST_TAG = "append_load"


@Composable
@ExcludeFromGeneratedCoverageReport
fun MoviesScreen(
    viewModel: MoviesViewModel = hiltViewModel(),
    onMovieClick: (movieId: Long) -> Unit,
) {
    val availableFilters by viewModel.availableFilters.collectAsStateWithLifecycle()
    val movieLazyPagingItems = viewModel.movies.collectAsLazyPagingItems()
    val onItemSelected: (MovieListFilterItem.FilterType) -> Unit = {
        viewModel.onEvent(MoviesViewModel.Event.OnFilterSelected(it))
    }

    MoviesScreen(
        movieLazyPagingItems = movieLazyPagingItems,
        filters = availableFilters,
        onMovieClick = onMovieClick,
        onFilterItemSelected = onItemSelected,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    movieLazyPagingItems: LazyPagingItems<Movie>,
    filters: List<MovieListFilterItem> = emptyList(),
    onMovieClick: (movieId: Long) -> Unit,
    onFilterItemSelected: (MovieListFilterItem.FilterType) -> Unit = {}
) {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Window)
    var query by rememberSaveable { mutableStateOf("") }
    var showSearch by rememberSaveable { mutableStateOf(false) }
    var isSearchActive by rememberSaveable { mutableStateOf(false) }
    val onSearchClicked: () -> Unit = {
        showSearch = true
        isSearchActive = true
    }

    val filterItemSelected: (MovieListFilterItem.FilterType) -> Unit = {
        when (it) {
            MovieListFilterItem.FilterType.DISCOVER -> {
                showSearch = true
                isSearchActive = true
            }

            else -> {
                showSearch = false
                onFilterItemSelected(it)
            }
        }
    }

    Background {
        Column(modifier = Modifier) {
            Column(modifier = Modifier.padding(horizontal = PlayGround.spacing.twoAndaHalf)) {
                Crossfade(targetState = showSearch, label = "toggleSearch") { show ->
                    when {
                        show -> {
                            SearchBarRow(
                                modifier = Modifier.statusBarsPadding(),
                                isSearchActive = isSearchActive,
                                query = query,
                                onQueryChange = { query = it },
                                onSearch = { isSearchActive = false },
                                onActiveChange = { showSearch = it },
                                onCancel = { showSearch = false },
                            )
                        }

                        else -> {
                            CenterAlignedTopAppBar(
                                modifier = Modifier.testTag(TITLE_TEST_TAG),
                                title = {
                                    Text(
                                        text = stringResource(id = R.string.movies_title),
                                        style = PlayGround.typography.titleMedium,
                                        color = PlayGround.color.onPrimary
                                    )
                                },
                                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                    containerColor = Color.Transparent,
                                    scrolledContainerColor = Color.Transparent
                                ),
                                actions = {
                                    Button(onClick = onSearchClicked) {
                                        Icon(
                                            painter = painterResource(id = com.example.core.ui.R.drawable.search),
                                            contentDescription = stringResource(
                                                id = R.string.movies_content_description_search
                                            )
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }

            val scrollState = rememberLazyStaggeredGridState()

            Box(modifier = Modifier.fillMaxSize()) {
                LazyVerticalStaggeredGrid(
                    modifier = Modifier
                        .padding(horizontal = PlayGround.spacing.twoAndaHalf)
                        .testTag(MOVIES_GRID_ITEMS_TEST_TAG)
                        .fillMaxSize(),
                    columns = StaggeredGridCells.Fixed(2),
                    state = scrollState,
                    verticalItemSpacing = PlayGround.spacing.oneAndHalf,
                    contentPadding = PaddingValues(bottom = PlayGround.spacing.twoAndaHalf),
                    horizontalArrangement = Arrangement.spacedBy(PlayGround.spacing.oneAndHalf),
                    content = {
                        item(span = StaggeredGridItemSpan.FullLine) { FiveVerticalSpacer() }
                        items(count = movieLazyPagingItems.itemCount) { index ->
                            val movie = movieLazyPagingItems[index]!!
                            val path = ImageUtil.buildImageUrl(movie.posterPath)
                            MoviePoster(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(ASPECT_RATIO),
                                posterUrl = path,
                                contentDescription = movie.title,
                                shimmer = shimmerInstance,
                                onClick = { onMovieClick(movie.id) }
                            )
                        }

                        if (movieLazyPagingItems.loadState.append == LoadState.Loading) {
                            item(span = StaggeredGridItemSpan.FullLine) {
                                Box (
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                ){
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .testTag(APPEND_LOAD_PROGRESS_TEST_TAG)
                                            .align(Alignment.Center)
                                            .padding(PlayGround.spacing.oneAndHalf),
                                    )
                                }
                            }
                        }
                    }
                )

                if (movieLazyPagingItems.loadState.refresh == LoadState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .testTag(FRESH_LOAD_PROGRESS_TEST_TAG)
                            .align(Alignment.Center)
                            .size(30.dp)
                            .padding(vertical = PlayGround.spacing.one),
                        strokeCap = StrokeCap.Round,
                        strokeWidth = 1.dp
                    )
                }
                MovieListFilter(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .testTag(CHIP_GROUP_TEST_TAG),
                    items = filters.map { Pair(filterLabelFor(filterItem = it), it.isSelected) },
                    onItemSelected = { filterItemSelected(filters[it].type) },
                )

            }
        }
    }
}
@Composable
private fun filterLabelFor(filterItem: MovieListFilterItem): String {
    return when (filterItem.type) {
        MovieListFilterItem.FilterType.NOW_PLAYING -> stringResource(R.string.now_playing)
        MovieListFilterItem.FilterType.POPULAR -> stringResource(R.string.popular)
        MovieListFilterItem.FilterType.TOP_RATED -> stringResource(R.string.top_rated)
        MovieListFilterItem.FilterType.UPCOMING -> stringResource(R.string.upcoming)
        MovieListFilterItem.FilterType.DISCOVER -> stringResource(R.string.discover)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarRow(
    modifier: Modifier = Modifier,
    isSearchActive: Boolean = false,
    isSearching: Boolean = false,
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
    onActiveChange: (Boolean) -> Unit = {},
    onCancel: () -> Unit = {},
) {
    var includeAdult by rememberSaveable { mutableStateOf(false) }
    var includeVideo by rememberSaveable { mutableStateOf(false) }

    SearchBar(
        modifier = modifier
            .testTag(SEARCH_TEST_TAG)
            .semantics { traversalIndex = -1f },
        query = query,
        shape = PlayGround.shape.large,
        tonalElevation = SearchBarDefaults.Elevation,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = isSearchActive,
        onActiveChange = onActiveChange,
        colors = SearchBarDefaults.colors(
            containerColor = PlayGround.color.primary,
            inputFieldColors = TextFieldDefaults.colors(
                focusedTextColor = contentColorFor(backgroundColor = PlayGround.color.primary),
                unfocusedTextColor = contentColorFor(backgroundColor = PlayGround.color.primary),
            ),
        ),
        placeholder = {
            Text(
                text = "Search ....",
                color = contentColorFor(backgroundColor = PlayGround.color.primary),
            )
        },
        leadingIcon = {
            IconButton(onClick = { onSearch(query) }) {
                Icon(
                    painter = painterResource(id = com.example.core.ui.R.drawable.search),
                    contentDescription = "perform search",
                    tint = contentColorFor(PlayGround.color.primary),
                )
            }
        },
        trailingIcon = {
            IconButton(onClick = onCancel) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "close search",
                    tint = contentColorFor(PlayGround.color.primary),
                )
            }
        },
    ) {
        AnimatedVisibility(visible = isSearching) {
            LinearProgressIndicator(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth(),
                strokeCap = StrokeCap.Round,
            )
        }

        TwoVerticalSpacer()
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = PlayGround.spacing.two,
                    )
                    .border(
                        1.dp,
                        color = PlayGround.color.onPrimary.copy(alpha = 0.5f),
                        shape = PlayGround.shape.medium,
                    )
                    .padding(
                        horizontal = PlayGround.spacing.two,
                    )
                    .weight(1F),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.include_adult),
                    style = PlayGround.typography.titleSmall,
                )
                FillingSpacer()
                Switch(checked = includeAdult, onCheckedChange = { includeAdult = it })
            }

            Row(
                modifier = Modifier
                    .padding(
                        horizontal = PlayGround.spacing.two,
                    )
                    .border(
                        1.dp,
                        color = PlayGround.color.onPrimary.copy(alpha = 0.5f),
                        shape = PlayGround.shape.medium,
                    )
                    .padding(
                        horizontal = PlayGround.spacing.two,
                    )
                    .weight(1F),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.include_video),
                    style = PlayGround.typography.titleSmall,
                )
                FillingSpacer()
                Switch(checked = includeVideo, onCheckedChange = { includeVideo = it })
            }
        }
    }
}

@ExcludeFromGeneratedCoverageReport
private fun testFilters() = listOf(
    MovieListFilterItem(
        isSelected = true,
        type = MovieListFilterItem.FilterType.NOW_PLAYING,
    ),
    MovieListFilterItem(
        isSelected = false,
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
)

@ExcludeFromGeneratedCoverageReport
private fun testMovies() = listOf(
    Movie(
        id = 667538,
        title = "Transformers: Rise of the Beasts",
        overview =
        """
                When a new threat capable of destroying the entire planet emerges, Optimus Prime and 
                the Autobots must team up with a powerful faction known as the Maximals. With the 
                fate of humanity hanging in the balance, humans Noah and Elena will do whatever it takes 
                to help the Transformers as they engage in the ultimate battle to save Earth.
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
)

@Suppress("MagicNumber")
@ThemePreviews
@Composable
@ExcludeFromGeneratedCoverageReport
fun MoviesScreenPreview() {
    PlayGroundTheme {
        MoviesScreen(
            movieLazyPagingItems = flowOf(PagingData.from(testMovies())).collectAsLazyPagingItems(),
            filters = testFilters(),
            onMovieClick = {},
        ) {}
    }
}
