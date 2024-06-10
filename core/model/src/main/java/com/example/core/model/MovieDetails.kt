package com.example.core.model

data class Genre(val id: Int, val name: String)

data class SpokenLanguage(
    val englishName: String,
    val iso6391: String,
    val name: String,
)

data class MovieDetails(
    val id: Long,
    val adult: Boolean,
    val backdropPath: String,
    val budget: Long,
    val genres: List<Genre> = arrayListOf(),
    val homepage: String,
    val imdbId: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val revenue: Long,
    val runtime: Int,
    val spokenLanguages: List<SpokenLanguage> = listOf(),
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Long,
)
