package com.example.screens.people.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.core.common.annotation.ExcludeFromGeneratedCoverageReport
import com.example.core.data.util.ImageUtil
import com.example.core.model.Person
import com.example.core.model.PersonMovie
import com.example.core.ui.component.Background
import com.example.core.ui.component.PersonItemView
import com.example.designSystem.core.annotation.ThemePreviews
import com.example.designSystem.core.theme.PlayGround
import com.example.designSystem.core.theme.PlayGroundTheme
import com.example.designSystem.core.theme.TwoAndHalfHorizontalSpacer
import com.example.screens.people.R
import com.example.screens.people.viewmodel.PeopleViewModel
import kotlinx.coroutines.flow.flowOf

const val PEOPLE_TITLE_TEST_TAG = "people_title"
const val REFRESH_PROGRESS_INDICATOR = "refresh_progress"
const val APPEND_PROGRESS_INDICATOR = "append_progress"
const val PEOPLE_LIST_TEST_TAG = "people_list"

@Composable
@ExcludeFromGeneratedCoverageReport
fun PeopleScreen(
    viewModel: PeopleViewModel = hiltViewModel(),
    onPersonClick: (Long) -> Unit = {},
) {
    val persons = viewModel.persons.collectAsLazyPagingItems()
    PeopleScreen(
        personLazyPagingItems = persons,
        onPersonClick = onPersonClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeopleScreen(
    personLazyPagingItems: LazyPagingItems<Person>,
    onPersonClick: (Long) -> Unit = {},
) {
    Background {
        Column(modifier = Modifier.fillMaxSize()) {
            CenterAlignedTopAppBar(
                modifier = Modifier.testTag(PEOPLE_TITLE_TEST_TAG),
                title = {
                    Text(
                        text = stringResource(R.string.people),
                        style = PlayGround.typography.titleMedium,
                        color = PlayGround.color.onPrimary,
                    )
                },
                windowInsets = TopAppBarDefaults.windowInsets,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent,
                ),
            )
            TwoAndHalfHorizontalSpacer()

            Box(modifier = Modifier.fillMaxSize()) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag(PEOPLE_LIST_TEST_TAG),
                    contentPadding = PaddingValues(
                        start = PlayGround.spacing.twoAndaHalf,
                        end = PlayGround.spacing.twoAndaHalf,
                        bottom = PlayGround.spacing.six,
                    ),
                    horizontalArrangement = Arrangement.spacedBy(PlayGround.spacing.oneAndHalf),
                    verticalArrangement = Arrangement.spacedBy(PlayGround.spacing.oneAndHalf),
                    columns = GridCells.Fixed(2),
                ) {
                    items(
                        count = personLazyPagingItems.itemCount,
                        key = personLazyPagingItems.itemKey { it.id },
                    ) {
                        val person = personLazyPagingItems[it]!!
                        val path = ImageUtil.buildImageUrl(
                            path = person.profilePath,
                            size = ImageUtil.Size.Profile.H632,
                        )
                        PersonItemView(
                            modifier = Modifier,
                            name = person.name,
                            knownFor = person.knownFor.joinToString(
                                ", ",
                            ) { personMovie -> personMovie.title },
                            imageUrl = path,
                            onClick = { onPersonClick(person.id) },
                        )
                    }

                    // append
                    if (personLazyPagingItems.loadState.append == LoadState.Loading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .testTag(APPEND_PROGRESS_INDICATOR)
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .padding(vertical = PlayGround.spacing.one),
                                    strokeWidth = 1.dp,
                                    strokeCap = StrokeCap.Round,
                                )
                            }
                        }
                    }
                }

                // refreshing
                if (personLazyPagingItems.loadState.refresh == LoadState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .testTag(REFRESH_PROGRESS_INDICATOR)
                            .size(30.dp)
                            .padding(vertical = PlayGround.spacing.one)
                            .align(Alignment.Center),
                        strokeWidth = 1.dp,
                        strokeCap = StrokeCap.Round,
                    )
                }
            }
        }
    }
}

@ExcludeFromGeneratedCoverageReport
private fun testPersons(): List<Person> = listOf(
    Person(
        id = 976,
        name = "Jason Statham",
        popularity = 199.055,
        profilePath = "/whNwkEQYWLFJA8ij0WyOOAD5xhQ.jpg",
        knownFor = listOf(
            PersonMovie(id = 107, title = "Snatch"),
            PersonMovie(id = 345940, title = "The Meg"),
            PersonMovie(id = 4108, title = "The Transporte"),
        ),
    ),
    Person(
        id = 3194176,
        name = "Angeli Khang",
        popularity = 190.975,
        profilePath = "/7vrTWF8PxQogF6o9ORZprYQoDOr.jpg",
        knownFor = listOf(
            PersonMovie(id = 931599, title = "Silip Sa Apoy"),
            PersonMovie(id = 1029446, title = "Selina's Gold"),
            PersonMovie(id = 893694, title = "Eva"),
        ),
    ),
    Person(
        id = 974169,
        name = "Jenna Ortega",
        popularity = 172.889,
        profilePath = "/q1NRzyZQlYkxLY07GO9NVPkQnu8.jpg",
        knownFor = listOf(
            PersonMovie(id = 119051, title = "Wednesday"),
            PersonMovie(id = 646385, title = "Scream"),
            PersonMovie(id = 760104, title = "X"),
        ),
    ),
    Person(
        id = 3234630,
        name = "Sangeeth Shobhan",
        popularity = 166.186,
        profilePath = "/7Vox31bH7XmgPNJzMKGa4uGyjW8.jpg",
        knownFor = listOf(
            PersonMovie(id = 1187075, title = "MAD"),
            PersonMovie(id = 138179, title = "Oka Chinna Family Story"),
            PersonMovie(id = 1119091, title = "Prema Vimanam"),
        ),
    ),
    Person(
        id = 27972,
        name = "Josh Hutcherson",
        popularity = 149.784,
        profilePath = "/npowygg8rH7uJ4v7rAoDMsHBhNq.jpg",
        knownFor = listOf(
            PersonMovie(id = 70160, title = "The Hunger Games"),
            PersonMovie(id = 101299, title = "The Hunger Games: Catching Fire"),
            PersonMovie(id = 131631, title = "The Hunger Games: Mockingjay - Part 1"),
        ),
    ),
    Person(
        id = 224513,
        name = "Florence Pugh",
        popularity = 115.26,
        profilePath = "/fhEsn35uAwUZy37RKpLdwWyx2y5.jpg",
        knownFor = listOf(
            PersonMovie(id = 530385, title = "Midsommar"),
            PersonMovie(id = 497698, title = "Black Widow"),
            PersonMovie(id = 331482, title = "Little Women"),
        ),
    ),
    Person(
        id = 18897,
        name = "Jackie Chan",
        popularity = 129.907,
        profilePath = "/nraZoTzwJQPHspAVsKfgl3RXKKa.jpg",
        knownFor = listOf(
            PersonMovie(id = 2109, title = "Rush Hour"),
            PersonMovie(id = 5175, title = "Rush Hour 2"),
            PersonMovie(id = 5174, title = "Rush Hour 3"),
        ),
    ),
    Person(
        id = 2359226,
        name = "Aya Asahina",
        popularity = 117.261,
        profilePath = "/dyqW1H1P56oEH2CmqfLvR39jfGA.jpg",
        knownFor = listOf(
            PersonMovie(id = 110316, title = "Alice in Borderland"),
            PersonMovie(id = 677602, title = "Grand Blue"),
            PersonMovie(id = 91414, title = "Runway 24"),
        ),
    ),
    Person(
        id = 1373737,
        name = "Florence Pugh",
        popularity = 115.26,
        profilePath = "/fhEsn35uAwUZy37RKpLdwWyx2y5.jpg",
        knownFor = listOf(
            PersonMovie(id = 530385, title = "Midsommar"),
            PersonMovie(id = 497698, title = "Black Widow"),
            PersonMovie(id = 331482, title = "Little Women"),
        ),
    ),
)

@Composable
@ThemePreviews
@ExcludeFromGeneratedCoverageReport
fun PeopleScreenPreview() {
    PlayGroundTheme {
        PeopleScreen(flowOf(PagingData.from(testPersons())).collectAsLazyPagingItems())
    }
}
