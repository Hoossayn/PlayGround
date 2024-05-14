package com.example.core.model

data class TVShow(
    val id: Long,
    val name: String,
    val overview: String,
    val backdropPath: String? = null,
    val posterPath: String? = null,
    val voteAverage: Double,
)
