package com.example.core.model

data class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    val backdropPath: String? = null,
    val posterPath: String? = null,
    val voteAverage: Double,
)
