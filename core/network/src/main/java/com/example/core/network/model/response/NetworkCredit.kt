package com.example.core.network.model.response

import com.example.core.model.Cast
import com.example.core.model.Crew
import com.example.core.model.MovieCredit
import com.google.gson.annotations.SerializedName

data class NetworkCrew(
    val adult: Boolean,
    @SerializedName("credit_id")
    val creditId: String,
    val department: String,
    val gender: Int,
    val id: Int,
    val job: String,
    @SerializedName("known_for_department")
    val knownForDepartment: String? = null,
    val name: String? = null,
    val title: String? = null,
    @SerializedName("original_name")
    val originalName: String? = null,
    val popularity: Double,
    @SerializedName("profile_path")
    val profilePath: String? = "",
    @SerializedName("poster_path")
    val posterPath: String? = null,
)

fun NetworkCrew.asDomainObject(): Crew = Crew(
    adult = adult,
    creditId = creditId,
    department = department,
    gender = gender,
    id = id,
    job = job,
    knownForDepartment = knownForDepartment,
    name = name,
    originalName = originalName,
    popularity = popularity,
    profilePath = profilePath,
    posterPath = posterPath,
)

data class NetworkCast(
    val adult: Boolean,
    @SerializedName("cast_id")
    val castId: Int,
    val character: String,
    @SerializedName("credit_id")
    val creditId: String,
    val gender: Int,
    val id: Int,
    @SerializedName("known_for_department")
    val knownForDepartment: String = "",
    val name: String? = null,
    val title: String? = null,
    val order: Int,
    @SerializedName("original_name")
    val originalName: String? = null,
    val popularity: Double,
    @SerializedName("profile_path")
    val profilePath: String? = "",
    @SerializedName("poster_path")
    val posterPath: String? = null,
)

fun NetworkCast.asDomainObject(): Cast = Cast(
    adult = adult,
    castId = castId,
    character = character,
    creditId = creditId,
    gender = gender,
    id = id,
    knownForDepartment = knownForDepartment,
    name = name,
    order = order,
    originalName = originalName,
    popularity = popularity,
    profilePath = profilePath,
    posterPath = posterPath,
    title = title,
    department = "Acting",
)

data class NetworkMovieCredit(
    val id: Int,
    val cast: List<NetworkCast> = listOf(),
    val crew: List<NetworkCrew> = listOf(),
)

fun NetworkMovieCredit.asDomainObject(): MovieCredit = MovieCredit(
    id = id,
    cast = cast.map { it.asDomainObject() },
    crew = crew.map { it.asDomainObject() },
)
