package com.example.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.common.annotation.ExcludeFromGeneratedCoverageReport
import com.example.core.model.MovieListFilterItem
import com.example.designSystem.core.annotation.ThemePreviews
import com.example.designSystem.core.theme.PlayGround
import com.example.designSystem.core.theme.PlayGroundTheme
import com.example.designSystem.core.theme.TwoAndHalfHorizontalSpacer

@ExcludeFromGeneratedCoverageReport
@Composable
private fun testFilters() = listOf(
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
    MovieListFilterItem(
        isSelected = false,
        type = MovieListFilterItem.FilterType.DISCOVER,
    ),
).map { Pair("Upcoming", it.isSelected) }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListFilter(
    modifier: Modifier = Modifier,
    items: List<Pair<String, Boolean>>,
    onItemSelected: (Int) -> Unit,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(PlayGround.spacing.one),
    ) {
        item { TwoAndHalfHorizontalSpacer() }
        itemsIndexed(items) { index, item ->
            FilterChip(
                selected = item.second,
                onClick = { onItemSelected(index) },
                label = { Text(text = item.first) },
                shape = PlayGround.shape.pill,
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = PlayGround.color.primary,
                    labelColor = PlayGround.color.onPrimary.copy(alpha = 0.7f),
                ),
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = PlayGround.color.onPrimary.copy(alpha = 0.7f),
                    enabled = true,
                    selected = item.second,
                ),
            )
        }
        item { TwoAndHalfHorizontalSpacer() }
    }
}

@ThemePreviews
@Composable
@ExcludeFromGeneratedCoverageReport
fun MovieListFilterPreview() {
    PlayGroundTheme {
        MovieListFilter(items = testFilters()) {}
    }
}
