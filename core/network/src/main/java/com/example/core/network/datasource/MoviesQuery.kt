package com.example.core.network.datasource

data class MoviesQuery(
    val language: String,
    val page: Int,
    val region: String?,
)
