package com.example.core.data.filter

import com.example.core.network.datasource.MoviesQuery

data class MoviesFilter(
    val language: String,
    val page: Int,
    val region: String?,
)

fun MoviesFilter.toQuery() = MoviesQuery(
    language = this.language,
    page = this.page,
    region = this.region,
)
