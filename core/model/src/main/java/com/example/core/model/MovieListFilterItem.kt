package com.example.core.model

data class MovieListFilterItem(val isSelected: Boolean, val type: FilterType) {
    enum class FilterType {
        NOW_PLAYING,
        POPULAR,
        TOP_RATED,
        UPCOMING,
        DISCOVER,
    }
}

data class TVShowsFilterItem(val isSelected: Boolean, val type: FilterType) {
    enum class FilterType {
        AIRING_TODAY,
        ON_THE_AIR,
        POPULAR,
        TOP_RATED,
        DISCOVER,
    }
}
