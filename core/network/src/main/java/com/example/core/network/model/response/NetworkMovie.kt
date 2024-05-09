package com.example.core.network.model.response

import com.example.core.model.Movie
import com.google.gson.annotations.SerializedName

data class NetworkMovie(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("genre_ids")
    val genreIds: List<Int> = listOf(),
    @SerializedName("id")
    val id: Long,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("video")
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
)

fun NetworkMovie.asDomainObject() = Movie(
    id = this.id,
    title = this.title,
    overview = this.overview,
    backdropPath = this.backdropPath,
    posterPath = this.posterPath,
    voteAverage = this.voteAverage,
)

fun NetworkMovie.equalsMovie(movie: Movie): Boolean =
    id == movie.id && title == movie.title && voteAverage == movie.voteAverage &&
        backdropPath == movie.backdropPath && posterPath == movie.posterPath &&
        overview == movie.overview
