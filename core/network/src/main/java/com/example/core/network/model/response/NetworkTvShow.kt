package com.example.core.network.model.response

import com.example.core.model.TVShow
import com.google.gson.annotations.SerializedName

data class NetworkTvShow(
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("first_air_date")
    val firstAirDate: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    val id: Long,
    val name: String,
    @SerializedName("origin_country")
    val originCountry: List<String>,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_name")
    val originalName: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
)

fun NetworkTvShow.asDomainObject() = TVShow(
    id = this.id,
    name = this.name,
    overview = this.overview,
    backdropPath = this.backdropPath,
    posterPath = this.posterPath,
    voteAverage = this.voteAverage,
)
